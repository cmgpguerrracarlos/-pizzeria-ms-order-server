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

    @GetMapping("/orders/ci/{ci}")
    public List<Order> getOrderByCi(@PathVariable("ci") String ci){
        return orderService.getOrderByCi(ci);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order order){
        var lista = order.getPizzaQuantityList();
        var total = lista.stream().map(p->p.getPrice()*p.getQuantity()).reduce(0.0,(acum,newVal)->acum+newVal);
        order.setTotal(total);
        return orderService.saveOrder(order);
    }

}
