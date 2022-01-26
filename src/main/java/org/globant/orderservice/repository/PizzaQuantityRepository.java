package org.globant.orderservice.repository;

import org.globant.orderservice.model.PizzaQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaQuantityRepository extends JpaRepository<PizzaQuantity, String> {
}