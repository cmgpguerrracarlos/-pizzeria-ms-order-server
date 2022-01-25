package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.external.PizzaService;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Primary
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PizzaService pizzaService;

    public List<Order> getAll(){
        log.info("find a list of all the orders ");
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        log.info("Retrieve the order with the id ");
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order){
        Float total = this.calculateTotal(order.getPizzaQuantityList());
        order.setTotal(total);
        log.info("Saved the new order");
        return orderRepository.save(order);
    }

    public List<Order> getOrderByCi(String ci) {
        log.info("Retrieve all the orders with the ci user ");
        return orderRepository.getByCiUser(ci);
    }

    private Float calculateTotal(List<PizzaQuantity> lista){
        return lista.stream()
                .map(p->pizzaService.getPriceByCode(p.getCode())*p.getQuantity())
                .reduce(0.0f, Float::sum);
    }

    private Map<String,Float> obtainListOfPrices(List<String> codes){
        //TODO USE OPENFEIGN TO GET THE LIST OF PRICES
        Map<String,Float> floatMap = new HashMap<>();
        for (String code: codes){
            floatMap.put(code,pizzaService.getPriceByCode(code));
        }
        return floatMap;
    }

}
