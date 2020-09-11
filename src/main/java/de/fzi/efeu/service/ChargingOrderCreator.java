package de.fzi.efeu.service;

import de.fzi.efeu.model.ChargingOrder;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.model.VehicleStationAssignment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class ChargingOrderCreator {

    public static ChargingOrder createChargingOrder(OffsetDateTime startTime, OffsetDateTime stopTime, Integer vehID) {

        // Daily fixed charging time blocks
        // Everyday from 5:00 to 5:45 a.m, 12:00 to 12:45 a.m
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // String today = df.format(new Date());
        ChargingOrder chargingOrder = new ChargingOrder();

        chargingOrder.setConnectionTime(startTime);
        chargingOrder.setDisconnectionTime(stopTime);
        chargingOrder.setVehicleId(vehID);
        chargingOrder.setChargingStationId(VehicleStationAssignment.assignVehicleToStation(vehID));

        return chargingOrder;
    }

    public static ChargingOrder createChargingOrder(OffsetDateTime startTime, Float vehSOC, Integer vehID) {

        // Daily fixed charging time blocks
        // Everyday from 5:00 to 5:45 a.m, 12:00 to 12:45 a.m
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // String today = df.format(new Date());
        ChargingOrder chargingOrder = new ChargingOrder();

        chargingOrder.setConnectionTime(startTime);
        // chargingOrder.setDisconnectionTime(stopTime);
        chargingOrder.setVehicleId(vehID);
        chargingOrder.setChargingStationId(VehicleStationAssignment.assignVehicleToStation(vehID));
        chargingOrder.setAmountEnergy(vehSOC);

        return chargingOrder;
    }

    public static boolean needToCreateChargingOrder(Float vehSOC)
    {
        boolean bRet;
        if( vehSOC<0.3f )
            bRet = true;
        else bRet = false;

        return bRet;
    }

}
