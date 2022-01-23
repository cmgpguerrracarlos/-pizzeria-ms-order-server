package org.globant.orderservice.controller;

import org.globant.orderservice.model.Order;
import org.globant.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    String baseUrl = "/orders";

    @MockBean
    OrderService orderService;

    @Test
    void testGetAll() throws Exception {

        given(orderService.getAll())
                .willReturn(Arrays.asList(Order.builder().id(1).ciUser("121212").total(12.5).build()));

        mockMvc.perform(get(baseUrl)).andExpect(status().is2xxSuccessful());
                //.andExpect((ResultMatcher) jsonPath("$[*].id").value(1));

        verify(orderService).getAll();
    }

    @Test
    void testGetTheOrder() {
    }

    @Test
    void testGetOrderByCi() {
    }

    @Test
    void testCreateOrder() {
    }
}