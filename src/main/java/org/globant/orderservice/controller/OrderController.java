package org.globant.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<List<Order>> getAll(){
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/orders/{id}")
    public Order getTheOrder(@PathVariable("id") int id){
        return orderService.getOrderById(id);
    }

    @GetMapping(value = "/orders/ci/{ci}")
    public List<Order> getOrderByCi(@PathVariable("ci") String ci){
        return orderService.getOrderByCi(ci);
    }

    @PostMapping(value = "/orders")
    public Order createOrder(@RequestBody Order order){
        return orderService.saveOrder(order);
    }

}
