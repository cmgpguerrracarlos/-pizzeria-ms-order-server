package org.globant.orderservice.dto;

import lombok.Data;

@Data
public class DtoPizza {
    private String uid;
    private Float price;
    private String style;
    private String size;
    private String topping;
}
