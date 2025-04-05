package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
