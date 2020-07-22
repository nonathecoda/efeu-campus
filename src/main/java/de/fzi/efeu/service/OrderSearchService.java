package de.fzi.efeu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.efeu.efeuportal.ApiException;
import de.fzi.efeu.efeuportal.api.OrderApi;
import de.fzi.efeu.efeuportal.model.EfCaOrder;
import de.fzi.efeu.efeuportal.model.EfCaOrderResp;
import de.fzi.efeu.util.OrderState;
import de.fzi.efeu.util.OrderUnit;

@Service
public class OrderSearchService {

    @Autowired
    private OrderApi orderApi;

    public List<EfCaOrder> findNewOrModifiedPackageOrders() throws ApiException {
        EfCaOrderResp newOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .state(OrderState.NEW.name())
                .orderUnit(OrderUnit.PACKAGE.name()));
        EfCaOrderResp modifiedOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .state(OrderState.MODIFIED.name())
                .orderUnit(OrderUnit.PACKAGE.name()));
        List<EfCaOrder> orders = new ArrayList<>();
        orders.addAll(newOrders.getOrders());
        orders.addAll(modifiedOrders.getOrders());
        return orders;
    }

    public List<EfCaOrder> findNewOrModifiedBoxOrders() throws ApiException {
        EfCaOrderResp newOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .state(OrderState.NEW.name())
                .orderUnit(OrderUnit.PACKAGE_BOX.name()));
        EfCaOrderResp modifiedOrders = orderApi.findOrdersByFinder(new EfCaOrder()
                .state(OrderState.MODIFIED.name())
                .orderUnit(OrderUnit.PACKAGE_BOX.name()));
        List<EfCaOrder> orders = new ArrayList<>();
        orders.addAll(newOrders.getOrders());
        orders.addAll(modifiedOrders.getOrders());
        return orders;
    }

    public List<EfCaOrder> findReadyForPlanningBoxOrders() throws ApiException {
        return orderApi.findOrdersByFinder(new EfCaOrder()
                .state(OrderState.READY_FOR_PLANNING.name())
                .orderUnit(OrderUnit.PACKAGE_BOX.name())).getOrders();
    }
}
