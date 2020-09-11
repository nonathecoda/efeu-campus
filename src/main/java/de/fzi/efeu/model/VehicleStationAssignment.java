package de.fzi.efeu.model;

import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.model.ChargingOrder;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import lombok.Getter;
import lombok.Setter;

public class VehicleStationAssignment {
    public static Integer assignVehicleToStation (Integer vehID) {
        Integer statID = vehID;
        return statID;
    } 
}
