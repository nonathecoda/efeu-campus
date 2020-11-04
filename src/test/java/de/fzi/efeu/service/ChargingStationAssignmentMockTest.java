/*package de.fzi.efeu.service;


import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ChargingStationApi;
import de.fzi.efeu.efeuportal.api.VehicleApi;
import de.fzi.efeu.efeuportal.model.EfCaChargingStation;
import de.fzi.efeu.efeuportal.model.EfCaVehicle;
import de.fzi.efeu.service.ChargingStationAssignment;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ChargingStationAssignmentMockTest {
    @Autowired
    private ChargingStationAssignment chargingStationAssignment;

    //@MockBean
    @Autowired
    private VehicleApi vehicleApi;

    //@MockBean
    @Autowired
    private ChargingStationApi chargingStationApi;

    @Test
    public void testChargingStationAssignmentMock() throws ApiException {
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

        //Post vehicle and charging station into Vehicle- and StationApi
        /*vehicleApi.postAddVehicles(vehicle1);
        vehicleApi.putVehicle(vehicle2);
        vehicleApi.putVehicle(vehicle3);
        vehicleApi.putVehicle(vehicle4);
        vehicleApi.putVehicle(vehicle5);*/

        /*chargingStationApi.putChargingStation(chargingStation1);
        chargingStationApi.putChargingStation(chargingStation2);
        chargingStationApi.putChargingStation(chargingStation3);
        chargingStationApi.putChargingStation(chargingStation4);
        chargingStationApi.putChargingStation(chargingStation5);*/

        /*String chargingStationId1 = chargingStationAssignment.getAssignedStation(vehicle1);
        String chargingStationId2 = chargingStationAssignment.getAssignedStation(vehicle2);
        String chargingStationId3 = chargingStationAssignment.getAssignedStation(vehicle3);
        String chargingStationId4 = chargingStationAssignment.getAssignedStation(vehicle4);
        String chargingStationId5 = chargingStationAssignment.getAssignedStation(vehicle5);

        //assertEquals(expected, actual)
        assertNotNull(chargingStationId1);
        assertEquals(chargingStation1.getIdent(), chargingStationId1);
        assertEquals(chargingStation2.getIdent(), chargingStationId2);
        assertEquals(chargingStation3.getIdent(), chargingStationId3);
        assertEquals(chargingStation4.getIdent(), chargingStationId4);
        assertEquals(chargingStation5.getIdent(), chargingStationId5);
    }

}

*/
