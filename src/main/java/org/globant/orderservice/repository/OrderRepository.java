package org.globant.orderservice.repository;

import org.globant.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> getByCiUser(String ci);

}