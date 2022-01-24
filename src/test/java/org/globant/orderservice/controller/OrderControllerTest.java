package org.globant.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    String baseUrl = "/orders";

    @MockBean
    OrderService orderService;

    List<Order> listMockOrders;

    @BeforeEach
    void setUp(){
        var listOfPizzasQuantity = Arrays.asList(new PizzaQuantity(1,"cvc",23.8,3));
        var order1 = Order.builder().id(1).ciUser("one").pizzaQuantityList(listOfPizzasQuantity).build();
        var order2 = Order.builder().id(2).ciUser("two").pizzaQuantityList(listOfPizzasQuantity).build();

        listMockOrders = Arrays.asList(order1,order2);
    }

    @Test
    void testGetAll() throws Exception {

        when(orderService.getAll()).thenReturn(listMockOrders);

        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].ciUser").value("one"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].ciUser").value("two"));
        verify(orderService).getAll();
    }

    @Test
    void testGetTheOrder() throws Exception {
        given(orderService.getOrderById(1)).willReturn(listMockOrders.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl+"/1").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.ciUser").value("one"));
        verify(orderService).getOrderById(1);    }

    @Test
    void testGetOrderByCi() {
    }

    @Test
    void testCreateOrder() {
    }
}