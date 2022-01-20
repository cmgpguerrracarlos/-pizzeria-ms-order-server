package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.globant.orderservice.repository.PizzaQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaQuantityRepository pizzaQuantityRepository;

    public List<Order> getAll(){
        log.info("findall de pizzarepository "+pizzaQuantityRepository.findAll());
        log.info("find all de order "+orderRepository.findAll());

        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        log.info("Obteniedndo la orden por el id " + orderRepository.findById(id).get());
        Order order = orderRepository.findById(id).get();
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getOrderByCi(String ci) {
        return orderRepository.getByCiUser(ci);
    }
}
