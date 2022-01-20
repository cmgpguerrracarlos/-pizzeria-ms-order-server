package org.globant.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.model.*;
import org.globant.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
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

    @GetMapping("/orders/{ci}")
    public List<Order> getOrderByCi(@PathVariable("ci") String ci){
        return orderService.getOrderByCi(ci);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order){
        Order response= orderService.saveOrder(order);
        log.info("Se guardo " + response);
        return response;
    }

}