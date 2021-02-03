package de.fzi.efeu.service;


import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ChargingStationApi;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;

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

    @Test
    public void testChargingStationAssignment() throws ApiException {

        EfCaChargingStation chargingStation1 = new EfCaChargingStation().ident("S1").latitude(49.116625).longitude(8.588302);
        EfCaChargingStation chargingStation2 = new EfCaChargingStation().ident("S2").latitude(49.117478).longitude(8.590465);
        EfCaChargingStation chargingStation3 = new EfCaChargingStation().ident("S3").latitude(49.116569).longitude(8.590589);;
        EfCaChargingStation chargingStation4 = new EfCaChargingStation().ident("S4").latitude(49.116422).longitude(8.590137);;

        List<EfCaChargingStation> chargingStations = List.of(chargingStation1, chargingStation2, chargingStation3, chargingStation4);

        VehicleStatus vehicle1;

        vehicle1 = new VehicleStatus();
        vehicle1.setLatitude((float) 41.62);
        vehicle1.setLongitude((float) 8.78);

        EfCaChargingStation nearestToVehicle1 = chargingStationAssignment.assignVehicleToStation(vehicle1);



        assertEquals("S4", nearestToVehicle1.getIdent());

    }

}



