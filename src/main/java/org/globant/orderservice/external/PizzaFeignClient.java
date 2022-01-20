package org.globant.orderservice.external;

import org.globant.orderservice.dto.DtoPizza;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("catalog-server")
public interface PizzaFeignClient {
    @GetMapping("/catalogs/{code}")
    DtoPizza getPizzaByCode(@PathVariable("code") String code);
}
