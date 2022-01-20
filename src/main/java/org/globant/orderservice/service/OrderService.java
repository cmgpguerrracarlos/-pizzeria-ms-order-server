package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.dto.DtoOrder;
import org.globant.orderservice.dto.DtoPizza;
import org.globant.orderservice.external.PizzaFeignClient;
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

    private PizzaFeignClient pizzaFeignClient;

    @Autowired
    private PizzaQuantityRepository pizzaQuantityRepository;

    public List<Order> getAll(){
        log.info("find a list of all the orders ");
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        log.info("Retrieve the order with the id ");
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order){
        log.info("Save the order");
        return orderRepository.save(order);
    }

    public List<Order> getOrderByCi(String ci) {
        log.info("Retrieve all the orders with the ci user ");
        return orderRepository.getByCiUser(ci);
    }

    public DtoOrder makeAnOrder(Order order){


        return new DtoOrder();
    }

    private Double totalPrice(List<PizzaQuantity> pizzas){
        return 0.0;
    }
}
