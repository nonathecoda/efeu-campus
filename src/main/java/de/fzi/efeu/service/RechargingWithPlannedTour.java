package de.fzi.efeu.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class RechargingWithPlannedTour {
    @Autowired
    OrderApi orderApi;

    @Autowired
    ProcessMgmtApi processMgmtApi;

    @Autowired
    private ChargingStationAssignment chargingStationAssignment;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ChargingStationApi chargingStationApi;

    @Autowired
    private BuildingApi buildingApi;

    @Value("${consumption.driving}") //Energieverbrauch während Fahren
    private Integer consumptionDriving;

    @Value("${consumption.standing}") //Standleistung
    private Integer consumptionStanding;

    @Value("${battery.capacity}")
    private Integer batteryCapacity;

    @Value("${charging.power}")
    private Integer chargingPower;

    //Get tour information
    List<EfCaTour> tours = new ArrayList<>();

    private void checkPlannedTour() throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());

        List<TourStatus> tourStatusList = processMgmtApi.getTourStatusList();

        for (TourStatus tourStatus : tourStatusList) {
            try {
                tours.add(objectMapper.readValue(tourStatus.getTourString(), EfCaTour.class));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

        //TODO: Sicherstellen --> One tour just for one vehicle? One tour multiple stops?
    List<Double> energyConsumption = new ArrayList<>(); //Calculate energy consumption of each stop
    List<OffsetDateTime> routeStartServiceTime = new ArrayList<>(); //Calculate start service time of each stop
    private void checkEnergyConsumptionPlannedTourPerVehicle() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        for (EfCaTour tour : tours) {
            for (EfCaVehicle vehicle : vehicles) { //Calculate energy consumption per vehicle
//1. Driving distance --> Fahrleistung: 0,074 Wh pro Meter (400Wh / 1,5m/s / 3600 s)
//2. Driving duration --> Standleistung
                if (vehicle.getIdent() == tour.getTourHeader().getVehicleExtId1()) {
                    //TODO: Which field can I use? tourDistance, startDistance, predDistance --> PTV
                    List<Integer> routeDistance = new ArrayList<>();
                    List<Integer> routeDuration = new ArrayList<>();
                    for (EfCaTourStop stop : tour.getTourStops()) {
                        routeDistance.add(stop.getPredDistance());
                        routeDuration.add(stop.getRouteDuration());
                        routeStartServiceTime.add(stop.getStartServiceTime());
                    }
                    //TODO: Can I use route duration? What's the definition of route duration? From depot to end of service time? --> PTV
                    //TODO: How to export a route plan? Robo 3T?
// Energy Consumption
                    for (int i = 0; i < routeDistance.size(); i++) {
                        energyConsumption.add(consumptionDriving / 1.5 / 3600 * routeDistance.get(i) + consumptionStanding * routeDuration.get(i));
                    }
                    createChargingOrderPlannedTour(vehicle);
                }
            }
        }
    }

    private void createChargingOrderPlannedTour(EfCaVehicle vehicle) throws ApiException {
        double energyConsumptionAtStop = 0;
        for (int j = 0; j < energyConsumption.size(); j++) {
            energyConsumptionAtStop = energyConsumptionAtStop + energyConsumption.get(j); //Total energy consumption along multiple stops
            if (energyConsumptionAtStop >= 0.5 * batteryCapacity) { //TODO: Wird der Tour erneut geplant?
                OffsetDateTime chargingStartTime = routeStartServiceTime.get(j); //Create a charging order before this stop
                //Create charging order with duration of energy consumption --> Battery full again
                long chargingDurationPlannedTour = (long) (energyConsumptionAtStop/chargingPower*3600);
                EfCaDateTimeSlot orderTimeSlot = new EfCaDateTimeSlot()
                        .start(chargingStartTime.minusHours(6)) //Dummy setting to ensure orderTimeSlot longer than pickup and delivery timeslot
                        .end(chargingStartTime.plusHours(6));
                EfCaDateTimeSlot pickupTimeSlot = new EfCaDateTimeSlot()
                        .start(chargingStartTime.minusSeconds(chargingDurationPlannedTour))
                        .end(chargingStartTime.plusSeconds(chargingDurationPlannedTour)); //Dummy
                EfCaDateTimeSlot deliveryTimeSlot = new EfCaDateTimeSlot()
                        .start(chargingStartTime)
                        .end(chargingStartTime.plusSeconds(chargingDurationPlannedTour)); //Delivery Slot is Charging time slot
                EfCaOrder rechargingOrder = new EfCaOrder()
                        .orderType(OrderType.RECHARGING.name())
                        .orderUnit(OrderUnit.PACKAGE_BOX.name())
                        .packageMode(1)
                        .state(OrderState.READY_FOR_PLANNING.name())
                        .orderTimeSlot(orderTimeSlot)
                        .pickupTimeSlots(List.of(pickupTimeSlot))
                        .deliveryTimeSlots(List.of(deliveryTimeSlot))
                        .pickup(createPickupStorage())
                        .delivery(createDeliveryStorage(vehicle, (int) (chargingDurationPlannedTour)))
                        .preassignedVehicleId(vehicle.getIdent())
                        .quantities(new EfCaQuantities().weight(0.1));
                //rechargingOrder.getPickup().getStorageIds().setChargingStationId(rechargingOrder.getDelivery().getStorageIds().getChargingStationId());
                chargingStationAssignment.assignVehicleToStation();
                rechargingOrder.getPickup().getStorageIds().setChargingStationId(chargingStationAssignment.getAssignedStation(vehicle));
                orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
            }
        }
    }

        //Pickup --> Dummy
        public EfCaStorage createPickupStorage () throws ApiException {
            EfCaStorage storage = new EfCaStorage();
            EfCaConnectionIds connectionIds = new EfCaConnectionIds();
            EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().type("DEPOT")).getBuildings().get(0);
            connectionIds.setBuildingId(building.getIdent());
            connectionIds.setAddressId(building.getAddressId());
            storage.storageIds(connectionIds);
            storage.setServiceTime(0);
            return storage;
        }

        //Delivery --> Charging Station
        public EfCaStorage createDeliveryStorage (EfCaVehicle vehicle, long duration) throws ApiException {
            EfCaStorage storage = new EfCaStorage();
            EfCaConnectionIds connectionIds = new EfCaConnectionIds();
            //EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().type("CHARGING_AREA")).getBuildings().get(0);
            //new EfCaBuilding().getChargingStationIds();
            //EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().chargingStationIds(chargingStationIds)).ident(chargingStationAssignment.getAssignedStation(vehicle));
            //connectionIds.setBuildingId(building.getIdent());
            //connectionIds.setAddressId(building.getAddressId());
            //connectionIds.setChargingStationId(building.getChargingStationIds().get(0));
            connectionIds.setChargingStationId(chargingStationAssignment.getAssignedStation(vehicle));
            connectionIds.setAddressId(chargingStationAssignment.getAssignedStation(vehicle));
            storage.storageIds(connectionIds);
            storage.setServiceTime((int) duration); //chargingDurationPlannedTour
            return storage;
        }
}




