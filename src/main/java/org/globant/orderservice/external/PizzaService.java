package org.globant.orderservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feignClientPizza", url = "http:localhost:9091/pizzas/")
public interface PizzaService{

    @GetMapping("/price/{code}")
    Double getPriceByCode(@PathVariable("code") String code);
}
