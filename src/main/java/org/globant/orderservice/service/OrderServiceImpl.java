package org.globant.orderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.globant.orderservice.exception.OrderNotFoundException;
import org.globant.orderservice.external.PizzaService;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Primary
public class OrderServiceImpl implements OrderService{

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
        Optional<Order> response = orderRepository.findById(id);
        if(response.isEmpty()){
            System.out.println(response);
            throw new OrderNotFoundException();
        }
        return response.get();
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
