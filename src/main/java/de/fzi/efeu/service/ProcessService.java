package de.fzi.efeu.service;

import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.ContactApi;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.model.EfCaModelCollector;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.util.OrderState;
import de.fzi.lieferbot.data.Operation;
import de.fzi.lieferbot.data.OperationType;
import de.fzi.lieferbot.data.Position;
import de.fzi.lieferbot.data.Schedule;
import de.fzi.lieferbot.data.Stop;

@Component
public class ProcessService {

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private ContactApi contactApi;

    @Value("${simulation.url}")
    private String simulationUrl;

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private BoxOrderService boxOrderService;

    @Autowired
    private OrderSearchService orderSearchService;

    public void fixTrip(final String id) {
        // ToDo: find trip by id
        // ToDo: set orders to fixed
        // ToDo: set timeslots and READY_FOR_PLANNING on associated pickup box orders
    }

    public void plan() throws ApiException, ParseException {
        List<EfCaOrder> packageOrders = orderSearchService.findNewOrModifiedPackageOrders();
        boxOrderService.createAndPersistMissingBoxOrders(packageOrders);
        List<EfCaOrder> boxOrders = orderSearchService.findNewOrModifiedBoxOrders();
        for (EfCaOrder boxOrder : boxOrders) {
            if (isBoxOrderReadyForPlanning(boxOrder)) {
                updateBoxOrderReadyForPlanning(boxOrder);
            }
        }
        List<EfCaOrder> readyForPlanningOrders = orderSearchService.findReadyForPlanningBoxOrders();
        EfCaModelCollector modelCollector = new EfCaModelCollector();
        for (final EfCaOrder readyForPlanningOrder : readyForPlanningOrders) {
            modelCollector.addIdentsItem(readyForPlanningOrder.getIdent());
        }
        orderApi.postAddWebApiOrder(modelCollector);
        //sendDummySchedule();

        //ToDo: start planning
    }

    private void sendDummySchedule() {
        RestTemplate restTemplate = new RestTemplate();
        Schedule schedule = new Schedule();
        LinkedList<Stop> stops = new LinkedList<>();

        Stop pickup = new Stop();
        pickup.setPosition(new Position(49.116854, 8.591011));
        pickup.setId(0);
        pickup.setTotalChargingBeforeTime(0);
        pickup.setTotalChargingAfterTime(0);
        HashSet<Operation> pickupOperations = new HashSet<>();
        pickupOperations.add(new Operation(OperationType.PICKUP, 0));
        pickup.setPickupOperations(pickupOperations);
        pickup.setDeliveryOperations(new HashSet<>());
        pickup.setTotalHandlingTime(60);
        pickup.setPostBoxId(0);

        Stop delivery = new Stop();
        delivery.setPosition(new Position(49.116164, 8.588954));
        delivery.setId(1);
        delivery.setTotalChargingBeforeTime(0);
        delivery.setTotalChargingAfterTime(0);
        HashSet<Operation> deliveryOperations = new HashSet<>();
        deliveryOperations.add(new Operation(OperationType.DELIVERY, 0));
        delivery.setDeliveryOperations(deliveryOperations);
        delivery.setPickupOperations(new HashSet<>());
        delivery.setTotalHandlingTime(60);
        delivery.setPostBoxId(1);

        stops.add(pickup);
        stops.add(delivery);
        schedule.setStops(stops);
        ResponseEntity<String> response = restTemplate.postForEntity(
                simulationUrl + "/schedules/0", schedule, String.class);
    }



    private boolean isBoxOrderReadyForPlanning(final EfCaOrder boxOrder) {
        // ToDo: consider pickup order
        return boxOrder.getPickup() != null && boxOrder.getDelivery() != null
                && boxOrder.getPickupTimeSlots() != null && boxOrder.getDeliveryTimeSlots() != null
                && !boxOrder.getPickupTimeSlots().isEmpty() && ! boxOrder.getDeliveryTimeSlots().isEmpty();
    }

    private void updateBoxOrderReadyForPlanning(final EfCaOrder boxOrder) throws ApiException {
        boxOrder.state(OrderState.READY_FOR_PLANNING.name());
        orderApi.putOrder(boxOrder);
    }
}
