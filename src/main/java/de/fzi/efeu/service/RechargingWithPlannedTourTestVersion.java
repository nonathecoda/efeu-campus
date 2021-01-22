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
import java.util.stream.Collectors;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RechargingWithPlannedTourTestVersion {
    @Autowired
    private OrderApi orderApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

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

    @Value("${thresholdSoC.plannedTour}")
    private Integer thresholdSoCPlannedTour;

    //Idea: check energy consumption of planned tours (until the next recharging stop) for each vehicle. If the energy
    //consumption more than a threshold, trigger creating a charging order. Start charging time should be the end of the
    //latest successfully planned tour.

    //Get tour information
    private List<EfCaTour> checkPlannedTour() throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());

        List<TourStatus> tourStatusList = processMgmtApi.getTourStatusList();
        List<EfCaTour> tours = new ArrayList<>();
        for (TourStatus tourStatus : tourStatusList) {
            try {
                tours.add(objectMapper.readValue(tourStatus.getTourString(), EfCaTour.class));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return tours;
    }

    //Select tours for vehicle and sort them based on their start time
    //private List<EfCaTour> selectAndSortTourPerVehicle(EfCaVehicle vehicle) throws ApiException {
    //    List<EfCaTour> tours = checkPlannedTour();
    private List<EfCaTour> selectAndSortTourPerVehicle(List<EfCaTour> tours, EfCaVehicle vehicle) throws ApiException {
        List <EfCaTour> toursForVehicle = new ArrayList<>();
        for (EfCaTour tour : tours){
            if (tour.getTourHeader().getVehicleExtId1().equals(vehicle.getIdent())){
                toursForVehicle.add(tour);
            }
        }
        //toursForVehicle.stream().sorted().collect(Collectors.toList());
        /*List <EfCaTour> sortedToursVehicle = toursForVehicle.stream().sorted((tour1, tour2)->tour1.getTourHeader().getStartDateTime()
                        .compareTo(tour2.getTourHeader().getStartDateTime())).collect(Collectors.toList());

        return sortedToursVehicle;*/

        //Sort tours based on their start time
        Collections.sort(toursForVehicle, new Comparator<EfCaTour>() {
            @Override
            public int compare(EfCaTour u1, EfCaTour u2) {
                return u1.getTourHeader().getStartDateTime().compareTo(u2.getTourHeader().getStartDateTime());
            }
        });
        return toursForVehicle;
    }

    //Note: One tour just for one vehicle. One tour contains multiple stops
    //Note: How to export a route plan? Robo 3T? Swagger while Simulation is running

    //Calculate energy consumption until the next charging stop
    private double checkEnergyConsumptionPlannedToursPerVehicle(EfCaVehicle vehicle) throws ApiException {
        List<EfCaTour> tours = checkPlannedTour();
        List<EfCaTour> toursForVehicle = selectAndSortTourPerVehicle(tours, vehicle);
        double energyConsumptionTour = 0;
        for (EfCaTour tourForVehicle : toursForVehicle) {
            for (EfCaTourStop stop : tourForVehicle.getTourStops()) {
                if (checkIfChargingStop(stop)){
                    break;
                }
                //Two factors determine energy consumption
                //1. Driving distance --> Fahrleistung: 0,074 Wh pro Meter (400Wh / 1,5m/s / 3600 s)
                //2. Driving duration --> Standleistung
                energyConsumptionTour += consumptionDriving / 1.5 / 3600 * stop.getPredDistance() + consumptionStanding / 3600 * stop.getRouteDuration(); //Wh
            }
        }
        return energyConsumptionTour;
    }

    //Get the latest unplanned tour --> get its start time and create recharging order at this time point
    private EfCaTour checkLatestPlannedTour(EfCaVehicle vehicle) throws ApiException {
        List<EfCaTour> tours = checkPlannedTour();
        List<EfCaTour> toursForVehicle = selectAndSortTourPerVehicle(tours, vehicle);
        List<EfCaTour> plannedTour = new ArrayList<>();
        outerLoop:
        for (EfCaTour tourForVehicle : toursForVehicle) {
            for (EfCaTourStop stop : tourForVehicle.getTourStops()) {
                if (checkIfChargingStop(stop)){
                    break outerLoop;
                }
            }
            plannedTour.add(tourForVehicle);
        }
        EfCaTour latestPlannedTour = plannedTour.get(plannedTour.size() - 1);
        return latestPlannedTour;
    }

    //Check if there is recharging stops along a tour (via action point type == "Recharging")
    private boolean checkIfChargingStop(EfCaTourStop stop) throws ApiException {
        for (EfCaTourActionPoint actionPoint: stop.getTourActionPoints()){
            EfCaOrderResp actionPointOrderResp = orderApi.findOrdersByFinder(new EfCaOrder().ident(actionPoint.getOrderExtId1())); //An action point has an unique OrderExtId1
            EfCaOrder actionPointOrder = actionPointOrderResp.getOrders().get(0); //This list only contains one element
            if (actionPointOrder.getOrderType().equals("RECHARGING")){
                return true;
            }
        }
        return false;
    }

    private void scheduleRechargingOrderPlannedTour(EfCaVehicle vehicle) throws ApiException {
        double energyConsumptionTours = checkEnergyConsumptionPlannedToursPerVehicle(vehicle);
        double currentVehicleSoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getStateOfCharge();
        if (energyConsumptionTours >= (currentVehicleSoC - thresholdSoCPlannedTour) * batteryCapacity) { //SoC always above 30%
            OffsetDateTime time = checkLatestPlannedTour(vehicle).getTourHeader().getEndDateTime();
            createRechargingOrderPlannedTour(time, vehicle, currentVehicleSoC, energyConsumptionTours);
        }
    }
    //For loop create for all vehicles --> Todo: who should run this method? --> Schnittstelle, evtl process management, wenn Tourplannung beginnt --> Rename
    private void scheduleRechargingOrderAllVehicles() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        for (EfCaVehicle vehicle : vehicles){
            scheduleRechargingOrderPlannedTour(vehicle);
        }
    }

    private void createRechargingOrderPlannedTour(OffsetDateTime chargingStartTime, EfCaVehicle vehicle, double currentVehicleSoC, double energyConsumption) throws ApiException {
        //Create a charging order before this tour
        // Charge battery until it's full again
        //ToDo:Optimierungsmöglichkeit: Charging duration
        long chargingDurationPlannedTour = (long) ((batteryCapacity-currentVehicleSoC*batteryCapacity+energyConsumption)/chargingPower*3600); //seconds
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
        rechargingOrder.getPickup().getStorageIds().setChargingStationId(chargingStationAssignment.getAssignedStation(vehicle));
        orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
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
        //TOdo Charging Station Address
        //TODO: in a list contains an element
        //EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().chargingStationIds(chargingStationAssignment.getAssignedStation(vehicle)));
        connectionIds.setAddressId(chargingStationAssignment.getAssignedStation(vehicle));
        storage.storageIds(connectionIds);
        storage.setServiceTime((int) duration); //chargingDurationPlannedTour
        return storage;
    }
}


