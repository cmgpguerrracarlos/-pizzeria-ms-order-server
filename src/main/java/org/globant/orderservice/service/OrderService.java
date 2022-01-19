package org.globant.orderservice.service;

import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.globant.orderservice.repository.PizzaQuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PizzaQuantityRepository pizzaQuantityRepository;

    public List<Order> getAll(){
        System.out.println(pizzaQuantityRepository.findAll());
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order, List<PizzaQuantity> pizzaQuantityList){
        pizzaQuantityRepository.saveAll(order.getPizzaQuantityList());
        return orderRepository.save(order);
    }
}
