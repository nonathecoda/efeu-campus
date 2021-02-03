package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
//@SpringBootApplication
public class ChargingStationAssignment {
    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ChargingStationApi chargingStationApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    public String getAssignedStationId(EfCaVehicle vehicle) throws ApiException {
        List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();

        Float lat_veh = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getLatitude();
        Float lon_veh = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getLongitude();

        EfCaChargingStation nearestStation = null;
        double nearestDistance = 999999999999.0;

        for (EfCaChargingStation chargingStation : chargingStations) {
            double el1 = 0.0, el2 = 0.0;
            double lat_cs = chargingStation.getLatitude();
            double lon_cs = chargingStation.getLongitude();
            double distance = harvineDist(lat_cs, lon_cs, lat_veh, lon_veh, el1, el2);
            if (distance < nearestDistance) {
                nearestStation = chargingStation;
                nearestDistance = distance;
            }
        }
        return nearestStation.getIdent();
    }

    private Double harvineDist(double lat_cs, double lon_cs, float lat_veh, float lon_veh, double el1, double el2) {
        double theta_lat = lat_cs - lat_veh;
        double theta_lon = lon_cs - lon_veh;
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(theta_lat);
        double lonDistance = Math.toRadians(theta_lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat_cs)) * Math.cos(Math.toRadians(lat_veh))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return distance;
    }

}
