package com.awanesh.APIGrocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.awanesh.APIGrocery.entity.Order;

@Repository
public interface OrderTableRepository extends JpaRepository<Order, Long> {

}
