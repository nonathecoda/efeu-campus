package de.fzi.efeu.service;


import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ChargingStationApi;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.VehicleStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ChargingStationAssignmentTest {
    @Autowired
    private ChargingStationAssignment chargingStationAssignment;

    //@MockBean
    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    //@MockBean
    @Autowired
    private ChargingStationApi chargingStationApi;

    private String getAssignedStationId_2(VehicleStatus vehicle) throws ApiException {
        Float lat_veh = vehicle.getLatitude();
        Float lon_veh = vehicle.getLongitude();

        EfCaChargingStation nearestStation = null;
        double nearestDistance = 999999999999.0;

        EfCaChargingStation chargingStation1 = new EfCaChargingStation().ident("S1").latitude(49.116625).longitude(8.588302);
        EfCaChargingStation chargingStation2 = new EfCaChargingStation().ident("S2").latitude(49.117478).longitude(8.590465);
        EfCaChargingStation chargingStation3 = new EfCaChargingStation().ident("S3").latitude(49.116569).longitude(8.590589);;
        EfCaChargingStation chargingStation4 = new EfCaChargingStation().ident("S4").latitude(49.116422).longitude(8.590137);;

        List<EfCaChargingStation> chargingStations = List.of(chargingStation1, chargingStation2, chargingStation3, chargingStation4);

        for (EfCaChargingStation chargingStation : chargingStations) {
            double el1 = 0.0, el2 = 0.0;
            double lat_cs = chargingStation.getLatitude();
            double lon_cs = chargingStation.getLongitude();
            double distance = chargingStationAssignment.harvineDist(lat_cs, lon_cs, lat_veh, lon_veh, el1, el2);
            if (distance < nearestDistance) {
                nearestStation = chargingStation;
                nearestDistance = distance;
            }
        }
        return nearestStation.getIdent();
    }
    @Test
    public void testChargingStationAssignment() throws ApiException {
        VehicleStatus vehicle1;

        vehicle1 = new VehicleStatus();
        vehicle1.setLatitude((float) 41.62);
        vehicle1.setLongitude((float) 8.78);

        String nearestToVehicle1 = getAssignedStationId_2(vehicle1);

        assertEquals("S4", nearestToVehicle1);

    }

}


