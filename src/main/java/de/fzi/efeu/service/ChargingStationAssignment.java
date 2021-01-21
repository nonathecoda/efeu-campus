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
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ChargingStationApi chargingStationApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AddressApi addressApi;

    @Autowired
    private BuildingApi buildingApi; //Later might be used


    // Vehicle-Station-Assignment: Vehicle i to Station i
    //TODO: Fahrzeug zur nahsten Ladestation

    private Map<String, String> assignVehicleToStation() throws ApiException {
        Map<String, String> mapVehicleStation = new HashMap<>();
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();
        for (int i = 0; i < vehicles.size(); i++) {
            mapVehicleStation.put(vehicles.get(i).getIdent(), chargingStations.get(i).getIdent());
        }
        return mapVehicleStation;
    }

    public String getAssignedStation(EfCaVehicle vehicle) throws ApiException {
        Map<String, String> mapVehicleStation = assignVehicleToStation();
        return mapVehicleStation.get(vehicle.getIdent());
    }

}
