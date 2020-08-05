package de.fzi.efeu.service;

import java.text.ParseException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ProcessMgmtApi;
import de.fzi.efeu.efeuportal.model.EfCaPlanningResp;

@Component
public class ProcessService {

    @Autowired
    private ProcessMgmtApi processMgmtApi;

    @Autowired
    private BoxOrderService boxOrderService;

    @Autowired
    private TourService tourService;

    @Autowired
    private VehicleCommunicationService vehicleCommunicationService;

    public void fixTrip(final String id) throws ParseException, ApiException {
        tourService.fixTour(id);
    }

    public void plan() throws ApiException, ParseException {
        boxOrderService.createAndPersistMissingBoxOrders();
        boxOrderService.setBoxOrdersReadyForPlanning();
        boxOrderService.exportReadyForPlanningOrders();
        // ToDo: get correct planning ID
        String planningId = UUID.randomUUID().toString();
        EfCaPlanningResp plan = processMgmtApi.executePlanning(planningId);
        tourService.processPlaningResults(plan);
        vehicleCommunicationService.sendVehicleSchedules(plan.getPlannedTours());
        // ToDo: handle unplanned orders?
    }


}
