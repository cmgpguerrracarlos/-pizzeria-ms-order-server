package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.external.PizzaService;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Primary
public class OrderServiceImpl implements OrderService{
    //TODO ADD EXCEPTIONS AND LOG SERVICE
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, PizzaService pizzaService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
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
        var returnOrder = calculateTotal(order);
        log.info("Saved the new order");
        return orderRepository.save(returnOrder);
    }

    public List<Order> getOrderByCi(String ci) {
        log.info("Retrieve all the orders with the ci user ");
        return orderRepository.getByCiUser(ci);
    }

    private Order calculateTotal(Order order){
        var listPizzasQuantity = order.getPizzaQuantityList();
        for(PizzaQuantity pizza: listPizzasQuantity){
            var price = pizzaService.getPriceByCode(pizza.getCode());
            pizza.setPrice(price);
        }
        order.setPizzaQuantityList(listPizzasQuantity);
        var total = listPizzasQuantity.stream()
                .map(p->pizzaService.getPriceByCode(p.getCode())*p.getQuantity())
                .reduce(0.0f, Float::sum);
        order.setTotal(total);
        return order;
    }

    public Order updateOrder(Order order){
        return orderRepository.save(order);
    }
    public void deleteOrderById(int id){
        orderRepository.deleteById(id);
    }

}
