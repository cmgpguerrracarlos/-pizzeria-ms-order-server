package org.globant.orderservice.repository;

import org.globant.orderservice.model.PizzaQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaQuantityRepository extends JpaRepository<PizzaQuantity, String> {
}