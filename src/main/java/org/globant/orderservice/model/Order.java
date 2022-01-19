package org.globant.orderservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String ciUser;
    private String Address;
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<PizzaQuantity> pizzaQuantityList;
}
