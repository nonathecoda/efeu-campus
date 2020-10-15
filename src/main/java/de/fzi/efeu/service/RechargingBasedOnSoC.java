package de.fzi.efeu.service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.TimeProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import de.fzi.efeu.model.ChargingStationAssignment;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@Service
public class RechargingBasedOnSoC {


    @Autowired
    private OrderApi orderApi;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private BoxMountingDeviceApi boxMountingDeviceApi;

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


    // SoC monitor
    // TODO 1-min-interval
    private Map<String, Integer> mapVehicleSoC = new HashMap<>();

    public void soCVehicle() throws ApiException {
        List<EfCaVehicle> vehicles = vehicleApi.getAllVehicles().getVehicles();
        for (final EfCaVehicle vehicle : vehicles) checkSoCVehicle(vehicle);
    }
    private int checkSoCVehicle(final EfCaVehicle vehicle) throws ApiException {
        int vehicleSoC = processMgmtApi.getVehicleStatus(vehicle.getIdent()).getRemainingRange();
        return vehicleSoC;
    }
    private int getVehicleSoC(final EfCaVehicle vehicle) throws ApiException {
        mapVehicleSoC.put(vehicle.getIdent(), checkSoCVehicle(vehicle));
        return mapVehicleSoC.get(vehicle.getIdent());
    }






}
