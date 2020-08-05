package de.fzi.efeu.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.model.EfCaModelCollector;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.util.OrderMode;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderType;
import de.fzi.efeu.util.OrderUnit;

@Service
public class BoxOrderService {

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private OrderSearchService orderSearchService;

    @Autowired
    private TimeSlotService timeSlotService;

    public void createAndPersistMissingBoxOrders() throws ApiException, ParseException {
        List<EfCaOrder> packageOrders = orderSearchService.findNewOrModifiedPackageOrders();
        for (EfCaOrder o : packageOrders) {
            if (o.getLinkedBoxOrderId() == null) {
                createMissingBoxOrdersForPackageOrder(o);
            }
        }
    }

    public void setBoxOrdersReadyForPlanning() throws ApiException {
        List<EfCaOrder> boxOrders = orderSearchService.findNewOrModifiedBoxOrders();
        for (EfCaOrder boxOrder : boxOrders) {
            if (isBoxOrderReadyForPlanning(boxOrder)) {
                updateBoxOrderReadyForPlanning(boxOrder);
            }
        }
    }

    public void exportReadyForPlanningOrders() throws ApiException {
        List<EfCaOrder> readyForPlanningOrders = orderSearchService.findReadyForPlanningBoxOrders();
        EfCaModelCollector modelCollector = new EfCaModelCollector();
        for (final EfCaOrder readyForPlanningOrder : readyForPlanningOrders) {
            modelCollector.addIdentsItem(readyForPlanningOrder.getIdent());
        }
        orderApi.postAddWebApiOrder(modelCollector);
    }

    private boolean isBoxOrderReadyForPlanning(final EfCaOrder boxOrder) {
        return boxOrder.getPickup() != null && boxOrder.getDelivery() != null
                && boxOrder.getPickupTimeSlots() != null && boxOrder.getDeliveryTimeSlots() != null
                && !boxOrder.getPickupTimeSlots().isEmpty() && ! boxOrder.getDeliveryTimeSlots().isEmpty();
    }

    private void updateBoxOrderReadyForPlanning(final EfCaOrder boxOrder) throws ApiException {
        boxOrder.state(OrderState.READY_FOR_PLANNING.name());
        orderApi.putOrder(boxOrder);
    }

    private void createMissingBoxOrdersForPackageOrder(final EfCaOrder packageOrder) throws ApiException,
            ParseException {
        EfCaOrder deliveryOrder = createDeliveryBoxOrder(packageOrder);
        EfCaOrder pickupOrder = createPickupBoxOrder(deliveryOrder);
        persistAndLinkBoxOrders(deliveryOrder, pickupOrder);
    }

    private void persistAndLinkBoxOrders(final EfCaOrder deliveryOrder, final EfCaOrder pickupOrder)
            throws ApiException {
        EfCaOrder deliveryOrderWithIdent = orderApi.postAddOrders(new EfCaModelCollector()
                .addOrdersItem(deliveryOrder)).getOrders().iterator().next();
        EfCaOrder pickupOrderWithIdent = orderApi.postAddOrders(new EfCaModelCollector()
                .addOrdersItem(pickupOrder)).getOrders().iterator().next();
        deliveryOrderWithIdent.setLinkedBoxOrderId(pickupOrderWithIdent.getIdent());
        pickupOrderWithIdent.setLinkedBoxOrderId(deliveryOrderWithIdent.getIdent());
        orderApi.putOrder(deliveryOrderWithIdent);
        orderApi.putOrder(pickupOrderWithIdent);
    }

    private EfCaOrder createDeliveryBoxOrder(final EfCaOrder packageOrder) throws ParseException, ApiException {
        EfCaOrder boxOrder = new EfCaOrder()
                .orderType(OrderType.DELIVERY.name())
                .orderMode(OrderMode.ASYNCHRON.name())
                .orderUnit(OrderUnit.PACKAGE_BOX.name())
                .state(OrderState.NEW.name())
                .pickup(packageOrder.getPickup())
                .delivery(packageOrder.getDelivery());
        boxOrder = timeSlotService.setBoxOrderTimeSlotsFromContacts(boxOrder);
        return boxOrder;
    }

    private EfCaOrder createPickupBoxOrder(final EfCaOrder deliveryOrder) {
        EfCaOrder boxOrder = new EfCaOrder()
                .orderType(OrderType.PICKUP.name())
                .orderMode(OrderMode.ASYNCHRON.name())
                .orderUnit(OrderUnit.PACKAGE_BOX.name())
                .state(OrderState.NEW.name())
                .pickup(deliveryOrder.getDelivery())
                .delivery(deliveryOrder.getPickup());
        return boxOrder;
    }
}
