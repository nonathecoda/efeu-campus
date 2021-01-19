package de.fzi.efeu.service;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.*;
import de.fzi.efeu.efeuportal.model.*;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;

public class RechargingWithPlannedTourSupporter {
    //Todo: Objectmapper testen?
    //Vehicle id
    //Tour
    //Header with vehicle Id
    //Stops (multiple)
    //stop.PredDistance()
    //stop.routeDuration
    //stop.getStartServiceTime()


}
