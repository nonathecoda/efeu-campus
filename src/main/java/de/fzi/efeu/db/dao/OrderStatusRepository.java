package de.fzi.efeu.db.dao;

import org.springframework.data.repository.CrudRepository;

import de.fzi.efeu.db.model.OrderStatus;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long> {
    OrderStatus findByOrderId(String orderId);
}
