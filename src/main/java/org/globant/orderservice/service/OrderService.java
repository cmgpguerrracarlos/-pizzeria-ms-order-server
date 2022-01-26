package org.globant.orderservice.service;

import org.globant.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAll();
    Order getOrderById(int id);
    Order saveOrder(Order order);
    List<Order> getOrderByCi(String ci);
    Order updateOrder(Order order);

    void deleteOrderById(int id);
}
