package de.fzi.efeu.service;

import de.fzi.efeu.model.ChargingOrder;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class ChargingOrderCreator {

    public ChargingOrder createChargingOrder () {

        // Daily fixed charging time blocks, independent of logistic tours
        // Daily from 1:00 to 5:00 a.m, 12:00 to 12:45 a.m
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(new Date()); // Get date

        ChargingOrder chargingOrder_fix = new ChargingOrder();

        chargingOrder_fix.setConnectionTime(OffsetDateTime.parse("2020-09-09T00:00-05:00"));
        chargingOrder_fix.setDisconnectionTime(OffsetDateTime.parse("2020-09-09T12:00-12:45"));

        chargingOrder_fix.setChargingStationId();
        chargingOrder_fix.setVehicleId();

        return chargingOrder_fix;

    }


}
