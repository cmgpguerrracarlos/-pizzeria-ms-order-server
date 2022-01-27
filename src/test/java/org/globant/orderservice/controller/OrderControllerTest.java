package org.globant.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.globant.orderservice.model.Order;
import org.globant.orderservice.model.PizzaQuantity;
import org.globant.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        var listOfPizzasQuantity = Arrays.asList(new PizzaQuantity(1,"cvc",23.8f,3));
        var order1 = Order.builder().id(1).ciUser("one").pizzaQuantityList(listOfPizzasQuantity).build();
        var order2 = Order.builder().id(2).ciUser("two").pizzaQuantityList(listOfPizzasQuantity).build();
        var order3 = Order.builder().id(3).ciUser("one").pizzaQuantityList(listOfPizzasQuantity).build();

        listMockOrders = Arrays.asList(order1,order2,order3);
    }

    @Test
    @DisplayName("Test getAll with valid values")
    void testGetAll() throws Exception {

        when(orderService.getAll()).thenReturn(listMockOrders);

        mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].ciUser").value("two"));
        verify(orderService).getAll();
    }

    @Test
    @DisplayName("Test getOrderById with valid values")
    void testGetTheOrder() throws Exception {
        given(orderService.getOrderById(1)).willReturn(listMockOrders.get(0));
        mockMvc.perform(get(baseUrl+"/1").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(jsonPath("$.id").value(1))
                        .andExpect(jsonPath("$.ciUser").value("one"));
        verify(orderService).getOrderById(1);
    }

    @Test
    @DisplayName("test getOrderByCi with valid values")
    void testGetOrderByCi() throws Exception {
        var listFiltered = listMockOrders.stream().filter(o-> o.getCiUser().equals("one")).collect(Collectors.toList());

        given(orderService.getOrderByCi("one")).willReturn(listFiltered);
        mockMvc.perform(get(baseUrl+"/ci/one").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].ciUser").value("one"))
                .andExpect(jsonPath("$[1].ciUser").value("one"));
        verify(orderService).getOrderByCi("one");
    }

    @Test
    @DisplayName("test saveOrder with valid values")
    void testCreateOrder() throws Exception {
        var returnOrder = Order.builder().id(1).ciUser("one").total(23f).build();
        given(orderService.saveOrder(returnOrder)).willReturn(returnOrder);
        String orderJsonString = mapper.writeValueAsString(returnOrder);
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJsonString)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    void updateOrder() throws Exception {
        var returnOrder = Order.builder().id(1).ciUser("one").total(23f).build();
        given(orderService.updateOrder(returnOrder)).willReturn(returnOrder);
        String orderJsonString = mapper.writeValueAsString(returnOrder);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put(baseUrl, returnOrder).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(this.mapper.writeValueAsBytes(returnOrder));
        mockMvc.perform(builder)
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void deleteOrder() throws Exception {
        OrderService serviceSpy = Mockito.spy(orderService);
        Mockito.doNothing().when(serviceSpy).deleteOrderById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl+"/1")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());

        verify(orderService).deleteOrderById(1);

    }
}