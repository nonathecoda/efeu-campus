package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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


    public EfCaChargingStation assignVehicleToStation(VehicleStatus vehStatus) throws ApiException {

        EfCaChargingStation chargingStation1 = new EfCaChargingStation().ident("S1").latitude(49.116625).longitude(8.588302);
        EfCaChargingStation chargingStation2 = new EfCaChargingStation().ident("S2").latitude(49.117478).longitude(8.590465);
        EfCaChargingStation chargingStation3 = new EfCaChargingStation().ident("S3").latitude(49.116569).longitude(8.590589);
        EfCaChargingStation chargingStation4 = new EfCaChargingStation().ident("S4").latitude(49.116422).longitude(8.590137);

        List<EfCaChargingStation> chargingStations = List.of(chargingStation1, chargingStation2, chargingStation3, chargingStation4);

        Float lat_veh = vehStatus.getLatitude();
        Float lon_veh = vehStatus.getLongitude();

        EfCaChargingStation nearestStation = null;
        double nearestDistance = 999999999999.0;

        for (EfCaChargingStation chargingStation : chargingStations) {


            double el1 = 0.0, el2 = 0.0;
            double lat_cs = chargingStation.getLatitude();
            double lon_cs = chargingStation.getLongitude();

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
            if (distance < nearestDistance) {
                nearestStation = chargingStation;
                nearestDistance = distance;
            }

        }

        return nearestStation;

    }


   public String getAssignedStation(EfCaVehicle vehicle) throws ApiException {

       VehicleStatus vehicleStat = null;
       try{
           vehicleStat = processMgmtApi.getVehicleStatus(vehicle.getIdent());
           if(null == vehicleStat){
               throw new ApiException("Process Mgmt Api failed to fetch correct vehicle.");
           }
       } catch (ApiException e) {
           System.out.println("Exception from process mgt api: "+e.getMessage());

       }


       EfCaChargingStation nearestStation = assignVehicleToStation(vehicleStat);

       return nearestStation.getIdent();

   }
}
