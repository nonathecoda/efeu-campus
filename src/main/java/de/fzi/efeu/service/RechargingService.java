package de.fzi.efeu.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaDateTimeSlot;
import de.fzi.efeu.efeuportal.model.EfCaModelCollector;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;

@Service
public class RechargingService {

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Value("${recharging.duration}")
    private Integer rechargingDuration;

    @Value("${recharging.frequency}")
    private Integer rechargingFrequency;

    private Map<String, Duration> offsetByVehicle = new HashMap<>();

    public void scheduleRechargingOrders() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        for (final EfCaVehicle vehicle : vehicles) {
            scheduleRechargingOrdersForVehicle(vehicle);
        }
    }

    private void scheduleRechargingOrdersForVehicle(final EfCaVehicle vehicle) throws ApiException {
        Duration vehicleOffset = getOffsetByVehicle(vehicle);
        // ToDo: consider offset
        OffsetDateTime latestCharging = getLatestChargingByVehicle(vehicle);
        scheduleRechargingOrdersForVehicleAndLatestTime(vehicle, latestCharging);
    }

    private OffsetDateTime getLatestChargingByVehicle(final EfCaVehicle vehicle) throws ApiException {
        // ToDo: preassigned Vehicle
        List<EfCaOrder> existingOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .orderType(OrderType.RECHARGING.name()))
                .getOrders();
        OffsetDateTime latest = null;
        for (final EfCaOrder existingOrder : existingOrders) {
            if (latest == null || existingOrder.getOrderTimeSlot().getStart().isAfter(latest)) {
                latest = existingOrder.getOrderTimeSlot().getStart();
            }
        }
        return latest;
    }

    private Duration getOffsetByVehicle(final EfCaVehicle vehicle) {
        if (!offsetByVehicle.containsKey(vehicle.getIdent())) {
            Duration largestOffset = offsetByVehicle
                    .values()
                    .stream()
                    .max(Duration::compareTo)
                    .orElse(Duration.ofMinutes(0));
            offsetByVehicle.put(vehicle.getIdent(), largestOffset.plus(Duration.ofMinutes(rechargingDuration)));
        }
        return offsetByVehicle.get(vehicle.getIdent());
    }

    private void scheduleRechargingOrdersForVehicleAndLatestTime(final EfCaVehicle vehicle,
            final OffsetDateTime latestChargingTime) throws ApiException {
        OffsetDateTime rolling = latestChargingTime;
        OffsetDateTime now = OffsetDateTime.now();
        if (rolling == null) {
            rolling = now;
        }
        OffsetDateTime endOfHorizon = now.plus(Duration.ofHours(24));
        rolling = rolling.plus(Duration.ofMinutes(rechargingFrequency));
        while (rolling.isBefore(endOfHorizon)) {
            scheduleRechargingOrdersForVehicleAndRollingTime(vehicle, rolling);
            rolling = rolling.plus(Duration.ofMinutes(rechargingFrequency));
        }
    }

    private void scheduleRechargingOrdersForVehicleAndRollingTime(final EfCaVehicle vehicle,
            final OffsetDateTime rolling) throws ApiException {
        EfCaDateTimeSlot dateTimeSlot = new EfCaDateTimeSlot()
                .start(rolling)
                .end(rolling.plusMinutes(rechargingDuration));
        // ToDo: clarify recharging order fields
        EfCaOrder rechargingOrder = new EfCaOrder()
                .orderType(OrderType.RECHARGING.name())
                .state(OrderState.NEW.name())
                .orderTimeSlot(dateTimeSlot);
        orderApi.postAddOrders(new EfCaModelCollector().addOrdersItem(rechargingOrder));
    }

}
