package de.fzi.efeu.service;

import de.fzi.efeu.model.ChargingOrder;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ChargingOrderCreator {

    public ChargingOrder createChargingOrder () {

        ChargingOrder chargingOrder = new ChargingOrder();

        chargingOrder.setStart(OffsetDateTime.parse("2020-07-31T08:00-06:00"));
        chargingOrder.setEnd(OffsetDateTime.parse("2020-07-31T10:00-06:00"));

        chargingOrder.setChargingStation();

        return chargingOrder;
    }


}
