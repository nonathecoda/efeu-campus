package de.fzi.efeu.service;

import de.fzi.efeu.api_model.ChargingOrder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ChargingOrderCreator {

    public ChargingOrder createChargingOrder () {

        ChargingOrder chargingOrder = new ChargingOrder();

        chargingOrder.setConnectionTime(OffsetDateTime.parse("2020-07-31T08:00-06:00"));
        chargingOrder.setDisconnectionTime(OffsetDateTime.parse("2020-07-31T10:00-06:00"));

        chargingOrder.setChargingStationId();
        chargingOrder.setVehicleId();

        return chargingOrder;

    }


}
