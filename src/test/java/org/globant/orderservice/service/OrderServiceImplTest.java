package org.globant.orderservice.service;

import org.globant.orderservice.external.PizzaService;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    PizzaService pizzaService;

    @InjectMocks
    OrderServiceImpl orderService;

    List<Order> orderList;

    @BeforeEach
    void setUp(){
        var order1 = Order.builder().id(1).ciUser("abc").total(100F).build();
        var order2 = Order.builder().id(2).ciUser("def").total(300F).build();
        var order3 = Order.builder().id(3).ciUser("abc").total(1F).build();
        orderList = Arrays.asList(order1,order2,order3);
    }

    @Test
    void getAll() {

        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        var result = orderService.getAll();
        System.out.println(result);
        assertEquals(3, result.size(),"Testing size of the result");
        assertEquals(2,orderList.get(1).getId(), "Testing id of the second value");
        assertEquals("def",orderList.get(1).getCiUser(), "Testing ci of the second value");

    }

    @Test
    void getOrderById() {
        Mockito.when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(orderList.get(0)));

        var result = orderService.getOrderById(1);

        assertNotNull(result,"Verify object is not nll");
        assertEquals(1,result.getId(), "Testing id of the value");
        assertEquals("abc",result.getCiUser(), "Testing ci of the value");
    }

    @Test
    void saveOrder() {
        var pizza = PizzaQuantity.builder().code("nQM").quantity(1).id(1).build();
        var newOrder = Order.builder().id(4).ciUser("xyz")
                .Address("dddd").pizzaQuantityList(Arrays.asList(pizza)).total(12F).build();
        Mockito.when(orderRepository.save(newOrder)).thenReturn(newOrder);
        Mockito.when(pizzaService.getPriceByCode("nQM")).thenReturn(234F);
        System.out.println(newOrder);
        var result = orderService.saveOrder(newOrder);
        assertNotNull(result,"Verify object is not nll");
        assertEquals(4,result.getId(), "Testing id of the result");
        assertEquals("xyz",result.getCiUser(), "Testing ci of the result");
    }

    @Test
    void getOrderByCi() {
        Mockito.when(orderRepository.getByCiUser("abc")).thenReturn(orderList.stream().filter(o->o.getCiUser().equals("abc")).collect(Collectors.toList()));

        var result = orderService.getOrderByCi("abc");

        assertNotNull(result,"Verify object is not nll");
        assertEquals(2,result.size(), "Testing size of the result");
    }


    @Test
    void updateOrder() {
        var newOrder = Order.builder().id(4).ciUser("xyz").total(12F).build();
        Mockito.when(orderRepository.save(newOrder)).thenReturn(newOrder);
        var result = orderService.updateOrder(newOrder);
        assertNotNull(result,"Verify object is not nll");
        assertEquals(4,result.getId(), "Testing id of the result");
        assertEquals("xyz",result.getCiUser(), "Testing ci of the result");
        assertEquals(12F,result.getTotal(),"Testing the total");
    }

    @Test
    void deleteOrder() {
        orderService.deleteOrderById(1);
        verify(orderRepository).deleteById(1);
    }
}