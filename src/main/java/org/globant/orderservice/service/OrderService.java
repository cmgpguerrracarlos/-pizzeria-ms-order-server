package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<Order> getAll(){
        log.info("find a list of all the orders ");
        return orderRepository.findAll();
    }

    public Order getOrderById(int id){
        log.info("Retrieve the order with the id ");
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order){
        var lista = order.getPizzaQuantityList();
        order.setTotal(getTotal(lista));
        return orderRepository.save(order);
    }

    public List<Order> getOrderByCi(String ci) {
        log.info("Retrieve all the orders with the ci user ");
        return orderRepository.getByCiUser(ci);
    }

    private Double getTotal(List<PizzaQuantity> lista){
        return lista.stream()
                .map(p->p.getPrice()*p.getQuantity())
                .reduce(0.0, Double::sum);
    }

}
