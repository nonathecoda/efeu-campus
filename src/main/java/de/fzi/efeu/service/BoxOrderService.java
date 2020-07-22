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
    private TimeSlotService timeSlotService;

    public void createAndPersistMissingBoxOrders(final List<EfCaOrder> packageOrders) throws ApiException, ParseException {
        for (EfCaOrder o : packageOrders) {
            if (o.getLinkedBoxOrderId() == null) {
                createMissingBoxOrdersForPackageOrder(o);
            }
        }
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
