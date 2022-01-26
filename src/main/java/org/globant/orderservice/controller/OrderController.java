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
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    public ResponseEntity<List<Order>> getAll(){
        try{
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/orders/{id}")
    public ResponseEntity<Order> getTheOrder(@PathVariable("id") int id){
        try{
            return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/orders/ci/{ci}")
    public ResponseEntity<List<Order>> getOrderByCi(@PathVariable("ci") String ci){
        try{
            return new ResponseEntity<>(orderService.getOrderByCi(ci), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
     try{
            return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    }

}
