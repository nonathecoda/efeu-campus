package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;
import de.fzi.efeu.util.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

class EmergencyCharging extends TimerTask {

    @Autowired
    private ChargingStationAssignment chargingStationAssignment;
    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AddressApi addressApi;

    @Autowired
    private BuildingApi buildingApi;

    @Autowired
    private ChargingStationApi chargingStationApi;

    @Value("${emergencyRecharging.duration}")
    private Integer emergencyRechargingDuration; //set 20 min in application.properties


    EmergencyCharging() //Constructor
    {
        // 1-min-interval
        Timer timer = new Timer();
        timer.schedule(new EmergencyCharging(), 0, 60000); //1 min = 600000 ms
    }

    @Override
    public void run() {
        try {
            List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
            float SoC;
            for (EfCaVehicle vehicle : vehicles) {
                SoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getStateOfCharge();
                if (SoC < 0.3f) {
                    OffsetDateTime now = timeProvider.now();
                    createRechargingOrderEmergency(now, vehicle);
                }
            }
            } catch(ApiException e){
                e.printStackTrace();
            }
        }
        

    private void createRechargingOrderEmergency(OffsetDateTime time, EfCaVehicle vehicle) throws ApiException {
            OffsetDateTime now = time;
            EfCaDateTimeSlot orderTimeSlot = new EfCaDateTimeSlot()
                    .start(now.minusHours(6)) //Dummy setting to ensure orderTimeSlot longer than pickup and delivery timeslot
                    .end(now.plusHours(6));
            EfCaDateTimeSlot pickupTimeSlot = new EfCaDateTimeSlot()
                    .start(now.minusSeconds(emergencyRechargingDuration))
                    .end(now.plusSeconds(emergencyRechargingDuration)); //Dummy
            EfCaDateTimeSlot deliveryTimeSlot = new EfCaDateTimeSlot()
                    .start(now)
                    .end(now.plusSeconds(emergencyRechargingDuration)); //Delivery Slot is Charging time slot
            EfCaOrder rechargingOrder = new EfCaOrder()
                    .orderType(OrderType.RECHARGING.name())
                    .orderUnit(OrderUnit.PACKAGE_BOX.name())
                    .packageMode(1)
                    .state(OrderState.READY_FOR_PLANNING.name())
                    .orderTimeSlot(orderTimeSlot)
                    .pickupTimeSlots(List.of(pickupTimeSlot))
                    .deliveryTimeSlots(List.of(deliveryTimeSlot))
                    .pickup(createPickupStorage())
                    .delivery(createDeliveryStorage(vehicle))
                    .preassignedVehicleId(vehicle.getIdent())
                    .quantities(new EfCaQuantities().weight(0.1));
            //rechargingOrder.getPickup().getStorageIds().setChargingStationId(rechargingOrder.getDelivery().getStorageIds().getChargingStationId());
            rechargingOrder.getPickup().getStorageIds().setChargingStationId(chargingStationAssignment.getAssignedStation(vehicle));
            orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
    }

    //Pickup --> Dummy
    private EfCaStorage createPickupStorage() throws ApiException {
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
    private EfCaStorage createDeliveryStorage(EfCaVehicle vehicle) throws ApiException {
        EfCaStorage storage = new EfCaStorage();
        EfCaConnectionIds connectionIds = new EfCaConnectionIds();

        //Todo: Find building by Charging Station Id
        //EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().type("CHARGING_AREA")).getBuildings().get(0);
        //new EfCaBuilding().getChargingStationIds();
        //EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().chargingStationIds(chargingStationIds)).ident(chargingStationAssignment.getAssignedStation(vehicle));
        //connectionIds.setBuildingId(building.getIdent());
        //connectionIds.setAddressId(building.getAddressId());
        //connectionIds.setChargingStationId(building.getChargingStationIds().get(0));
        connectionIds.setChargingStationId(chargingStationAssignment.getAssignedStation(vehicle));
        connectionIds.setAddressId(chargingStationAssignment.getAssignedStation(vehicle));
        storage.storageIds(connectionIds);
        storage.setServiceTime(emergencyRechargingDuration);
        return storage;
    }
}
