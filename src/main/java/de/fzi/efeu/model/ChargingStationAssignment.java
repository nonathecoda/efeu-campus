package de.fzi.efeu.model;

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
    private BuildingApi buildingApi;

    // Assign stations to vehicles
    private Map<String, String> mapVehicleStation = new HashMap<>();

    // Vehicle-Station-Assignment: Vehicle i to Station i
    // Assumption: only 5 robots and 5 stations and stations are in hub
    //TODO: Fahrzeug zur nahsten Ladestation
    //TODO: wie wärs wenn der Übergabebock besetzt ist?

    public void getListVehicleAndStation() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();
    }

    public void assignVehicleToStation(List<EfCaVehicle> vehicles, List<EfCaChargingStation> chargingStations) throws ApiException {
        for (int i = 0; i < 5; i++) {
            mapVehicleStation.put(vehicles.get(i).getIdent(), chargingStations.get(i).getIdent());
        }
    }

    public String getAssignedStation(final EfCaVehicle vehicle) throws ApiException {
        return mapVehicleStation.get(vehicle.getIdent());
    }
}
