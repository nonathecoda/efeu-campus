package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;
import de.fzi.efeu.util.TimeProvider;
import de.fzi.efeu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

@Service
public class EmergencyCharging {


    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private BoxMountingDeviceApi boxMountingDeviceApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AddressApi addressApi;

    @Autowired
    private BuildingApi buildingApi;

    @Value("${emergencyRecharging.duration}")
    private Integer emergencyRechargingDuration; //set 20 min

    EmergencyCharging() //Constructor
    {
        // 1-min-interval
        Timer timer = new Timer();
        timer.schedule(new OneMinuteInterval(), 0, 60000); //1 min = 600000 ms
    }
    // SoC monitor
/*    private Map<String, Integer> mapVehicleSoC = new HashMap<>(); --> Don't need a map

    public List<EfCaVehicle> listVehicle() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        return vehicles;
    }

    private int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        mapVehicleSoC.put(vehicle.getIdent(), checkSoCVehicle(vehicle));
        return mapVehicleSoC.get(vehicle.getIdent());
    }
*/

    public int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        int vehicleSoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getRemainingRange(); //Assume: RemainingRange is x%.
        return vehicleSoC;
    }

    private EfCaStorage createPickupStorage() throws ApiException {
        EfCaStorage storage = new EfCaStorage();
        EfCaConnectionIds connectionIds = new EfCaConnectionIds();
        EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().type("DEPOT"))
                .getBuildings().get(0);
        connectionIds.setBuildingId(building.getIdent());
        connectionIds.setAddressId(building.getAddressId());
        storage.storageIds(connectionIds);
        storage.setServiceTime(0);
        return storage;
    }

    private EfCaStorage createDeliveryStorage() throws ApiException {
        EfCaStorage storage = new EfCaStorage();
        EfCaConnectionIds connectionIds = new EfCaConnectionIds();
        EfCaBuilding building = buildingApi.findBuildingsByFinder(new EfCaBuilding().type("CHARGING_AREA"))
               .getBuildings().get(0);
        connectionIds.setBuildingId(building.getIdent());
        connectionIds.setAddressId(building.getAddressId());
        connectionIds.setChargingStationId(building.getChargingStationIds().get(0));
        storage.storageIds(connectionIds);
        storage.setServiceTime(emergencyRechargingDuration);
        return storage;
    }

    public class OneMinuteInterval extends TimerTask {
        public void run() {
            try {
                List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
                int currentSoC = 0;
                for (EfCaVehicle vehicle : vehicles) {
                    currentSoC = getVehicleSoC(vehicle);
                    if (currentSoC < 30) {
                        OffsetDateTime now = timeProvider.now();
                        //TODO: Create charging order with duration 20 min
                        //TODO: Flxexibel duration
                        EfCaDateTimeSlot orderTimeSlot = new EfCaDateTimeSlot()
                                    .start(now.minusHours(6))
                                    .end(now.plusHours(6));
                        EfCaDateTimeSlot pickupTimeSlot = new EfCaDateTimeSlot()
                                    .start(now.minusSeconds(emergencyRechargingDuration))
                                    .end(now.plusSeconds(emergencyRechargingDuration));
                        EfCaDateTimeSlot deliveryTimeSlot = new EfCaDateTimeSlot()
                                    .start(now)
                                    .end(now.plusSeconds(emergencyRechargingDuration));
                        EfCaOrder rechargingOrder = new EfCaOrder()
                                    .orderType(OrderType.RECHARGING.name())
                                    .orderUnit(OrderUnit.PACKAGE_BOX.name())
                                    .packageMode(1)
                                    .state(OrderState.READY_FOR_PLANNING.name())
                                    .orderTimeSlot(orderTimeSlot)
                                    .pickupTimeSlots(List.of(pickupTimeSlot))
                                    .deliveryTimeSlots(List.of(deliveryTimeSlot))
                                    .pickup(createPickupStorage())
                                    .delivery(createDeliveryStorage())
                                    .preassignedVehicleId(vehicle.getIdent())
                                    .quantities(new EfCaQuantities().weight(0.1));
                        //rechargingOrder.getPickup().getStorageIds().setChargingStationId(rechargingOrder.getDelivery().getStorageIds().getChargingStationId());
                        rechargingOrder.getPickup().getStorageIds().setChargingStationId(ChargingStationAssignment.getAssignedStation());
                        orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
                    }
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}

