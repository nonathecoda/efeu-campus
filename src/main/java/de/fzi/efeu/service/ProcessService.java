package de.fzi.efeu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaOrderResp;

@Component
public class ProcessService {

    @Autowired
    private OrderApi orderApi;

    public void fixTrip(final String id) {
        // ToDo: fix trip
    }

    public void plan() {
        try {
            EfCaOrderResp orders = orderApi.findOrdersByFinder(new EfCaOrder());
            if (orders.getOrders() != null && newOrUnplannedOrders(orders)) {
                triggerPlanning(orders);
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }


    private void triggerPlanning(final EfCaOrderResp orders) {
        orders.getOrders()
                .stream()
                .filter(o -> o.getLinkedBoxOrderId() == null)
                .forEach(this::createBoxOrder);
        // ToDo: start planning
        // ToDo: retrieve plan
        // ToDo: send plan to vehicles
    }

    private void createBoxOrder(final EfCaOrder order) {
        EfCaOrder boxOrder = new EfCaOrder();
        try {
            EfCaOrderResp boxOrderResponse = orderApi.postAddOrders(List.of(boxOrder));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private boolean newOrUnplannedOrders(final EfCaOrderResp orders) {
        for (final EfCaOrder order : orders.getOrders()) {
            return true;
        }
        return false;
    }
}
