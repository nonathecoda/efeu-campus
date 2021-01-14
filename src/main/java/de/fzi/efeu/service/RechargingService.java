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
import de.fzi.efeu.util.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RechargingService {

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
//Value adjustment under resources.application.properties
    @Value("${recharging.duration}")
    private Integer rechargingDuration;

    @Value("${recharging.frequency}")
    private Integer rechargingFrequency;


    private Map<String, Duration> offsetByVehicle = new HashMap<>();

    public void scheduleRechargingOrders() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        OffsetDateTime now = timeProvider.now(); //now: orders have been planned
        for (final EfCaVehicle vehicle : vehicles) scheduleRechargingOrdersForVehicle(vehicle, now);
    }

    private void scheduleRechargingOrdersForVehicle(final EfCaVehicle vehicle,
            final OffsetDateTime now) throws ApiException {
        Duration vehicleOffset = getOffsetByVehicle(vehicle); //1.Duration
        List<EfCaOrder> existingOrders = getChargingOrdersByVehicle(vehicle);//2.Planned recharging orders
        scheduleRechargingOrdersForVehicleAndLatestTime(vehicle, existingOrders, vehicleOffset, now);
    }

    //Put charging duration to all vehicles.
    private Duration getOffsetByVehicle(final EfCaVehicle vehicle) {
        if (!offsetByVehicle.containsKey(vehicle.getIdent())) {
            Duration largestOffset = offsetByVehicle
                    .values()
                    .stream()
                    .max(Duration::compareTo)
                    .orElse(null); //check the longest duration
            if (largestOffset == null) {
                offsetByVehicle.put(vehicle.getIdent(), Duration.ZERO);
            } else {
                offsetByVehicle.put(vehicle.getIdent(), largestOffset.plusSeconds(rechargingDuration));
            }
        }
        return offsetByVehicle.get(vehicle.getIdent());
    }

    private List<EfCaOrder> getChargingOrdersByVehicle(final EfCaVehicle vehicle) throws ApiException {
        List<EfCaOrder> existingOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .orderType(OrderType.RECHARGING.name())
                .preassignedVehicleId(vehicle.getIdent()))
                .getOrders(); //Find recharging orders for vehicles and store them in existingOrders
        if (existingOrders != null) {
            existingOrders = existingOrders
                    .stream()
                    .filter(efCaOrder -> efCaOrder.getPreassignedVehicleId() != null && efCaOrder.getPreassignedVehicleId().equals(vehicle.getIdent())).collect(Collectors.toList());
        }
        if (existingOrders != null && !existingOrders.isEmpty()) {
            existingOrders.sort(Comparator.comparing((EfCaOrder o) -> o.getOrderTimeSlot().getStart()));
        } //Sort existing orders based on start time
        return existingOrders;
    }

    private void scheduleRechargingOrdersForVehicleAndLatestTime(final EfCaVehicle vehicle,
            final List<EfCaOrder> existingOrders,
            final Duration vehicleOffset,
            final OffsetDateTime now) throws ApiException {
        if (existingOrders == null || existingOrders.isEmpty()) {
            OffsetDateTime endOfCharging = scheduleFirstRechargingOrder(vehicle, vehicleOffset, now);
            scheduleSecondRechargingOrder(vehicle, vehicleOffset, endOfCharging);
        }
        if (existingOrders.size() == 1) {
            scheduleSecondRechargingOrder(vehicle, vehicleOffset, getEndOfCharging(existingOrders.get(0)));
        }
    }

    private OffsetDateTime scheduleFirstRechargingOrder(final EfCaVehicle vehicle, final Duration vehicleOffset,
                                                        final OffsetDateTime now) throws ApiException {
        OffsetDateTime start = now.plusSeconds(rechargingFrequency);
        start = start.plus(vehicleOffset); //End of charging
        scheduleRechargingOrderForVehicleAndTime(vehicle, start);
        return start;  //Assume: at the beginning robot has full battery
    }

    private void scheduleSecondRechargingOrder(final EfCaVehicle vehicle, final Duration vehicleOffset,
            final OffsetDateTime previousStart) throws ApiException {
        OffsetDateTime start = previousStart.plusSeconds(rechargingFrequency);
        scheduleRechargingOrderForVehicleAndTime(vehicle, start);
    }

    private OffsetDateTime getEndOfCharging(final EfCaOrder order) {
        return order.getDeliveryTimeSlots().get(0).getEnd();
    }

    private OffsetDateTime scheduleRechargingOrderForVehicleAndTime(final EfCaVehicle vehicle,
            final OffsetDateTime time) throws ApiException {
        EfCaDateTimeSlot orderTimeSlot = new EfCaDateTimeSlot()
                .start(time.minusHours(6))
                .end(time.plusHours(6));
        EfCaDateTimeSlot pickupTimeSlot = new EfCaDateTimeSlot()
                .start(time.minusSeconds(rechargingDuration))
                .end(time.plusSeconds(rechargingDuration));
        EfCaDateTimeSlot deliveryTimeSlot = new EfCaDateTimeSlot()
                .start(time)
                .end(time.plusSeconds(rechargingDuration));
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
        rechargingOrder.getPickup().getStorageIds().setChargingStationId(rechargingOrder.getDelivery().getStorageIds().getChargingStationId());
        orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
        return deliveryTimeSlot.getEnd();
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
        storage.setServiceTime(rechargingDuration);
        return storage;
    }
}
