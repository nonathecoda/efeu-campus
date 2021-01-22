package de.fzi.efeu.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;

public class RechargingWithPlannedTourSupporter {
    @Autowired
    private RechargingWithPlannedTour rechargingWithPlannedTour;

    @Autowired
    OrderApi orderApi;

    @Autowired
    ProcessMgmtApi processMgmtApi;

    @Autowired
    private ChargingStationAssignment chargingStationAssignment;

    @Autowired
    private VehicleApi vehicleApi;

    @Autowired
    private ChargingStationApi chargingStationApi;

    @Autowired
    private BuildingApi buildingApi;

    @Value("${consumption.driving}") //Energieverbrauch während Fahren
    private Integer consumptionDriving;

    @Value("${consumption.standing}") //Standleistung
    private Integer consumptionStanding;

    @Value("${battery.capacity}")
    private Integer batteryCapacity;

    @Value("${charging.power}")
    private Integer chargingPower;

    
    //Todo: Objectmapper testen?
    private List<EfCaTour> checkPlannedTour() throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());

        List<TourStatus> tourStatusList = processMgmtApi.getTourStatusList();
        List<EfCaTour> tours = new ArrayList<>();
        for (TourStatus tourStatus : tourStatusList) {
            try {
                tours.add(objectMapper.readValue(tourStatus.getTourString(), EfCaTour.class));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return tours;
    }
    //Vehicle id
    EfCaVehicle vehicle1 = new EfCaVehicle().ident("V1");
    EfCaVehicle vehicle2 = new EfCaVehicle().ident("V2");
    List<EfCaVehicle> vehicles = List.of(vehicle1, vehicle2);

    //Tour TODO: fill tour with header and stop, how?
    EfCaTour tour1 = new EfCaTour();
    EfCaTour tour2 = new EfCaTour();
    List<EfCaTour> tours = List.of(tour1, tour2);

    //Header with vehicle Id
    EfCaTourHeader header1 = new EfCaTourHeader().vehicleExtId1("V1");
    EfCaTourHeader header2 = new EfCaTourHeader().vehicleExtId1("V2");
    //Stops (multiple)
    //stop.predDistance()
    //stop.routeDuration()
    //stop.getStartServiceTime()
    EfCaTourStop stop1 = new EfCaTourStop().predDistance(400)
            .routeDuration(600)
            .startServiceTime(OffsetDateTime.parse("2020-10-26T12:00"));
    EfCaTourStop stop2 = new EfCaTourStop().predDistance(100)
            .routeDuration(100)
            .startServiceTime(OffsetDateTime.parse("2020-10-26T12:20"));
    EfCaTourStop stop3 = new EfCaTourStop().predDistance(1000)
            .routeDuration(800)
            .startServiceTime(OffsetDateTime.parse("2020-10-26T12:20"));
    EfCaTourStop stop4 = new EfCaTourStop().predDistance(100)
            .routeDuration(100)
            .startServiceTime(OffsetDateTime.parse("2020-10-26T12:20"));

}
