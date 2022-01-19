package org.globant.orderservice.controller;

import org.globant.orderservice.model.*;
import org.globant.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAll(){
        return orderService.getAll();
    }
    @GetMapping("/orders/{id}")
    public Order getTheOrder(@PathVariable("id") int id){
        return orderService.getOrderById(id);
    }
    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order){
        return orderService.saveOrder(order);
    }

}
