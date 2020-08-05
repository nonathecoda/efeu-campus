package de.fzi.efeu.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.fzi.efeu.db.dao.TourStatusRepository;
import de.fzi.efeu.db.model.TourState;
import de.fzi.efeu.db.model.TourStatus;
import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaOrderResp;
import de.fzi.efeu.efeuportal.model.EfCaPlanningResp;
import de.fzi.efeu.efeuportal.model.EfCaTour;
import de.fzi.efeu.efeuportal.model.EfCaTourActionPoint;
import de.fzi.efeu.efeuportal.model.EfCaTourStop;
import de.fzi.efeu.util.OrderType;

@Service
public class TourService {

    @Autowired
    private TourStatusRepository tourStatusRepository;

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private TimeSlotService timeSlotService;

    @Transactional
    public void processPlaningResults(final EfCaPlanningResp planningResponse) {
        deleteTours(planningResponse);
        persistNewTours(planningResponse);
    }

    private void persistNewTours(final EfCaPlanningResp planningResponse) {
        for (final EfCaTour plannedTour : planningResponse.getPlannedTours()) {
            TourStatus tourStatus = new TourStatus();
            tourStatus.setTour(plannedTour);
            tourStatus.setTourId(plannedTour.getTourHeader().getExtId1());
            tourStatus.setTourState(TourState.PLANNED);
            tourStatus.setVehicleId(plannedTour.getTourHeader().getVehicleExtId1());
            tourStatusRepository.save(tourStatus);
        }
    }

    private void deleteTours(final EfCaPlanningResp planningResponse) {
        for (final String deletedTour : planningResponse.getDeletedTours()) {
            tourStatusRepository.deleteTourStatusByTourId(deletedTour);
        }
    }

    @Transactional
    public void fixTour(final String id) throws ApiException, ParseException {
        TourStatus tourStatus = tourStatusRepository.findTourStatusByTourId(id);
        tourStatus.setTourState(TourState.FIXED);
        tourStatusRepository.save(tourStatus);
        // ToDo: Fixierung in efeuPortal
        // ToDo: Fixierung von orders
        updateLinkedBoxOrders(tourStatus);

    }

    private void updateLinkedBoxOrders(final TourStatus tourStatus) throws ApiException, ParseException {
        for (final EfCaTourStop tourStop : tourStatus.getTour().getTourStops()) {
            for (final EfCaTourActionPoint tourActionPoint : tourStop.getTourActionPoints()) {
                updateLinkedBoxOrdersForActionPoint(tourStop, tourActionPoint);
            }
        }
    }

    private void updateLinkedBoxOrdersForActionPoint(final EfCaTourStop tourStop,
            final EfCaTourActionPoint tourActionPoint) throws ApiException, ParseException {
        EfCaOrderResp orderResp = orderApi.findOrdersByFinder(new EfCaOrder().ident(tourActionPoint.getOrderExtId1()));
        EfCaOrder deliveryOrder = orderResp.getOrders().iterator().next();
        if (deliveryOrder.getOrderType().equals(OrderType.DELIVERY.name())) {
            EfCaOrderResp pickupOrderResp = orderApi.findOrdersByFinder(
                    new EfCaOrder().ident(deliveryOrder.getLinkedBoxOrderId()));
            EfCaOrder pickupOrder = pickupOrderResp.getOrders().iterator().next();
            timeSlotService.setBoxOrderDeliveryTimeSlotsFromContact(pickupOrder);
            timeSlotService.setBoxOrderPickupTimeSlotsFromStart(pickupOrder, tourStop.getEndServiceTime().plusHours(2));
            orderApi.putOrder(pickupOrder);
        }
    }
}
