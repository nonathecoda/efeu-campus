package de.fzi.efeu.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;

public class RechargingWithPlannedTourSupporter {
    //Todo: Objectmapper testen?
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
