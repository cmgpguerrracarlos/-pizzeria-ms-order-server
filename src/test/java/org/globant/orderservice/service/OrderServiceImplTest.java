package org.globant.orderservice.service;

import org.globant.orderservice.model.Order;
import org.globant.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        int resultLengthExpected = 2;
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        var result = orderService.getAll();

        assertEquals(resultLengthExpected, result.size(),"Testing size of the result");
        assertEquals(3,orderList.get(1).getId(), "Testing id of the second value");
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
        //TODO SAVE ORDER SERVICE
    }

    @Test
    void getOrderByCi() {
        //TODO GET ORDER BY CI SERVICE
        Mockito.when(orderRepository.getByCiUser("abc")).thenReturn(orderList.stream().filter(o->o.getCiUser().equals("abc")).collect(Collectors.toList()));

        var result = orderService.getOrderByCi("abc");

        assertNotNull(result,"Verify object is not nll");
        assertEquals(2,result.size(), "Testing size of the result");
    }


    @Test
    void updateOrder() {
        //TODO UPDATE ORDER SERVICE
    }

    @Test
    void deleteOrder() {
        //TODO DELETE ORDER SERVICE
    }
}