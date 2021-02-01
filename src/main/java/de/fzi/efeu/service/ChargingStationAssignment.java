package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
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

    // Vehicle-Station-Assignment: Vehicle i to Station i
    //TODO: Fahrzeug zur nahsten Ladestation

    public Map<String, String> assignVehicleToStation() throws ApiException {
        Map<String, String> mapVehicleStation = new HashMap<>();
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();

        List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();
        for (int i = 0; i < vehicles.size(); i++) {
            mapVehicleStation.put(vehicles.get(i).getIdent(), chargingStations.get(i).getIdent());
        }
        return mapVehicleStation;
    }

    public Map<EfCaChargingStation, Double> assignVehicleToStation(EfCaVehicle vehicle) throws ApiException {
        System.out.println("Hello");
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();

        List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();
        //Hashmap<vehicles,Integer> vehiclemap = null;
        //private Map<String, Duration> offsetByVehicle = new HashMap<>()
         Map<String,Integer> vehiclemap = new HashMap<>();
        //List<Integer> listlat = null;
        Float lat_veh = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getLatitude();


        Float lon_veh = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getLongitude();

        Map<EfCaChargingStation, Double>  distMap= new HashMap<>();
        for(int j=0; j<=chargingStations.size(); j++){


            double lat_cs = chargingStations.get(j).getLatitude();
            double lon_cs = chargingStations.get(j).getLongitude();

            double theta_lat = lat_cs - lat_veh;
            double theta_lon = lon_cs - lon_veh;

            double distance = 3;
            distMap.put(chargingStations.get(j),distance );

           // return distMap;

           //double theta = lon1 - lon2;
           //double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
              //      + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
              //      * Math.cos(deg2rad(theta));
            //dist = Math.acos(dist);
           // dist = rad2deg(dist);
           // dist = dist * 60; // 60 nautical miles per degree of sepera


            //String std = chargingStationApi.getAllChargingStations().getChargingStations().
            //System.out.println(j);

        }


            // return map
        return distMap;
    }


    //public String getAssignedStation(EfCaVehicle vehicle) throws ApiException {
       // Map<String, String> mapVehicleStation = assignVehicleToStation();
      //  return mapVehicleStation.get(vehicle.getIdent());
   // }

}
