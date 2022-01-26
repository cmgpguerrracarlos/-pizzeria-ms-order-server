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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;
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

        orderList = Arrays.asList(order1,order2);
    }

    @Test
    void getAll() {
        int resultLengthExpected = 2;
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);

        var result = orderService.getAll();

        assertEquals(resultLengthExpected, result.size(),"Testing size of the result");
        assertEquals(2,orderList.get(1).getId(), "Testing id of the second value");
        assertEquals("def",orderList.get(1).getCiUser(), "Testing ci of the second value");

    }

    @Test
    void getOrderById() {
    }

    @Test
    void saveOrder() {
    }

    @Test
    void getOrderByCi() {
    }
}