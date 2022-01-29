package org.globant.orderservice.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="catalog", url="http://host.docker.internal:8000/pizzas")
public interface PizzaService{

    @GetMapping("/price/{code}")
    Float getPriceByCode(@PathVariable("code") String code);
}
