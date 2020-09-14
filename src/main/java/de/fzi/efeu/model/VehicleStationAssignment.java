package de.fzi.efeu.model;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ChargingStationApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class VehicleStationAssignment {

    private VehicleApi vehicleApi;
    private ChargingStationApi chargingStationApi;

    private Map<String, String> vehicleAssignments;

    @Autowired
    public VehicleStationAssignment(VehicleApi vehicleApi, ChargingStationApi chargingStationApi) {
        this.vehicleApi = vehicleApi;
        this
        vehicleAssignments = new HashMap<>();
        try {
            List<EfCaChargingStation> chargingStations = chargingStationApi.getAllChargingStations().getChargingStations();
            for (EfCaVehicle vehicle : vehicleApi.getAllVehicles().getVehicles()) {
                vehicleAssignments.put(vehicle.getIdent(), chargingStations.get(0).getIdent());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public String assignVehicleToStation (String vehID) {
        return vehicleAssignments.get(vehID);
    } 
}
