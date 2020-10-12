package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.TimeProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RechargingBasedOnSoC {


    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private AddressApi addressApi;

    @Autowired
    private BuildingApi buildingApi;

    @Value("${recharging.StateOfCharge}")
    private Integer soCVehicle;


    private Map<String, Integer> mapVehicleSoC = new HashMap<>();

    public void soCVehicle() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        for (final EfCaVehicle vehicle : vehicles) checkSoCVehicle(vehicle);
    }

    //Do I need a timestamp in input? How to present the constant monitoring of SoC?
    private int checkSoCVehicle(final EfCaVehicle vehicle) throws ApiException {
        int vehicleSoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getRemainingRange();
        return vehicleSoC;
    }

    private int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        mapVehicleSoC.put(vehicle.getIdent(), checkSoCVehicle(vehicle));
        return mapVehicleSoC.get(vehicle.getIdent());
    }



}
