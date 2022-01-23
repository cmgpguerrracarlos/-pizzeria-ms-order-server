package org.globant.orderservice.service;

import org.globant.orderservice.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order getOrderById(int id);
    Order saveOrder(Order order);
    List<Order> getOrderByCi(String ci);

}
