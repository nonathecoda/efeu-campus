package de.fzi.efeu.model;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ChargingStationAssignmentTest {
    @Autowired
    ChargingStationAssignment chargingStationAssignment;

    @Test
    public void testChargingStationAssignment() throws ApiException {
        List<EfCaChargingStation> chargingStations = new ArrayList<>();
        List<EfCaVehicle> vehicles = new ArrayList<>();

        EfCaChargingStation chargingStation1 = new EfCaChargingStation().ident("S1");
        EfCaChargingStation chargingStation2 = new EfCaChargingStation().ident("S2");
        EfCaChargingStation chargingStation3 = new EfCaChargingStation().ident("S3");
        EfCaChargingStation chargingStation4 = new EfCaChargingStation().ident("S4");
        EfCaChargingStation chargingStation5 = new EfCaChargingStation().ident("S5");

        chargingStations.add(chargingStation1);
        chargingStations.add(chargingStation2);
        chargingStations.add(chargingStation3);
        chargingStations.add(chargingStation4);
        chargingStations.add(chargingStation5);

        EfCaVehicle vehicle1 = new EfCaVehicle().ident("V1");
        EfCaVehicle vehicle2 = new EfCaVehicle().ident("V2");
        EfCaVehicle vehicle3 = new EfCaVehicle().ident("V3");
        EfCaVehicle vehicle4 = new EfCaVehicle().ident("V4");
        EfCaVehicle vehicle5 = new EfCaVehicle().ident("V5");

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);

        chargingStationAssignment.assignVehicleToStation(vehicles, chargingStations);
        String chargingStationId1 = chargingStationAssignment.getAssignedStation(vehicle1);
        String chargingStationId2 = chargingStationAssignment.getAssignedStation(vehicle2);
        String chargingStationId3 = chargingStationAssignment.getAssignedStation(vehicle3);
        String chargingStationId4 = chargingStationAssignment.getAssignedStation(vehicle4);
        String chargingStationId5 = chargingStationAssignment.getAssignedStation(vehicle5);

        //assertEquals(expected, actual)
        assertEquals(chargingStation1.getIdent(), chargingStationId1);
        assertEquals(chargingStation2.getIdent(), chargingStationId2);
        assertEquals(chargingStation3.getIdent(), chargingStationId3);
        assertEquals(chargingStation4.getIdent(), chargingStationId4);
        assertEquals(chargingStation5.getIdent(), chargingStationId5);
    }

}
