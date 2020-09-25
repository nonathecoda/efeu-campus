package de.fzi.efeu.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.AddressApi;
import de.fzi.efeu.efeuportal.api.BuildingApi;
import de.fzi.efeu.efeuportal.api.ChargingStationApi;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaBuilding;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.EfCaConnectionIds;
import de.fzi.efeu.efeuportal.model.EfCaDateTimeSlot;
import de.fzi.efeu.efeuportal.model.EfCaModelCollector;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaQuantities;
import de.fzi.efeu.efeuportal.model.EfCaStorage;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;
import de.fzi.efeu.util.OrderMode;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;
import de.fzi.efeu.util.TimeProvider;

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

    @Value("${recharging.duration}")
    private Integer rechargingDuration;

    @Value("${recharging.frequency}")
    private Integer rechargingFrequency;

    private Map<String, Duration> offsetByVehicle = new HashMap<>();

    public void scheduleRechargingOrders() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        OffsetDateTime now = timeProvider.now();
        for (final EfCaVehicle vehicle : vehicles) {
            scheduleRechargingOrdersForVehicle(vehicle, now);
        }
    }

    private void scheduleRechargingOrdersForVehicle(final EfCaVehicle vehicle,
            final OffsetDateTime now) throws ApiException {
        Duration vehicleOffset = getOffsetByVehicle(vehicle);
        List<EfCaOrder> existingOrders = getChargingOrdersByVehicle(vehicle);
        scheduleRechargingOrdersForVehicleAndLatestTime(vehicle, existingOrders, vehicleOffset, now);
    }

    private List<EfCaOrder> getChargingOrdersByVehicle(final EfCaVehicle vehicle) throws ApiException {
        List<EfCaOrder> existingOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .orderType(OrderType.RECHARGING.name())
                .preassignedVehicleId(vehicle.getIdent()))
                .getOrders();
        if (existingOrders != null) {
            existingOrders = existingOrders
                    .stream()
                    .filter(efCaOrder -> efCaOrder.getPreassignedVehicleId() != null && efCaOrder.getPreassignedVehicleId().equals(vehicle.getIdent())).collect(Collectors.toList());
        }
        if (existingOrders != null && !existingOrders.isEmpty()) {
            existingOrders.sort(Comparator.comparing((EfCaOrder o) -> o.getOrderTimeSlot().getStart()));
        }
        return existingOrders;
    }

    private Duration getOffsetByVehicle(final EfCaVehicle vehicle) {
        if (!offsetByVehicle.containsKey(vehicle.getIdent())) {
            Duration largestOffset = offsetByVehicle
                    .values()
                    .stream()
                    .max(Duration::compareTo)
                    .orElse(null);
            if (largestOffset == null) {
                offsetByVehicle.put(vehicle.getIdent(), Duration.ZERO);
            } else {
                offsetByVehicle.put(vehicle.getIdent(), largestOffset.plusSeconds(rechargingDuration));
            }
        }
        return offsetByVehicle.get(vehicle.getIdent());
    }

    private void scheduleRechargingOrdersForVehicleAndLatestTime(final EfCaVehicle vehicle,
            final List<EfCaOrder> existingOrders,
            final Duration vehicleOffset,
            final OffsetDateTime now) throws ApiException {
        if (existingOrders == null || existingOrders.isEmpty()) {
            OffsetDateTime start = scheduleFirstRechargingOrder(vehicle, vehicleOffset, now);
//            scheduleSecondRechargingOrder(vehicle, vehicleOffset, chargingStation, start);
        }
        if (existingOrders.size() == 1) {
//            scheduleSecondRechargingOrder(vehicle, vehicleOffset, chargingStation,
//                    existingOrders.get(0).getOrderTimeSlot().getStart());
        }
    }

    private void scheduleSecondRechargingOrder(final EfCaVehicle vehicle, final Duration vehicleOffset,
            final OffsetDateTime previousStart) throws ApiException {
        OffsetDateTime start = previousStart.plusSeconds(rechargingFrequency);
        scheduleRechargingOrderForVehicleAndTime(vehicle, start);
    }

    private OffsetDateTime scheduleFirstRechargingOrder(final EfCaVehicle vehicle, final Duration vehicleOffset,
            final OffsetDateTime now) throws ApiException {
        OffsetDateTime start = now.plusSeconds(rechargingFrequency);
        start = start.plus(vehicleOffset);
        scheduleRechargingOrderForVehicleAndTime(vehicle, start);
        return start;
    }

    private void scheduleRechargingOrderForVehicleAndTime(final EfCaVehicle vehicle,
            final OffsetDateTime time) throws ApiException {
        EfCaDateTimeSlot orderTimeSlot = new EfCaDateTimeSlot()
                .start(time)
                .end(time.plusHours(24));
//                .end(time.plusSeconds(rechargingDuration));
        EfCaDateTimeSlot pickupTimeSlot = new EfCaDateTimeSlot()
                .start(time)
                .end(time.plusHours(24));
//                .end(time.plusSeconds(rechargingDuration));
        EfCaDateTimeSlot deliveryTimeSlot = new EfCaDateTimeSlot()
                .start(pickupTimeSlot.getStart())
                .end(pickupTimeSlot.getEnd());
        EfCaOrder rechargingOrder = new EfCaOrder()
                .orderType(OrderType.RECHARGING.name())
                .orderMode(OrderMode.ASYNCHRON.name())
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
