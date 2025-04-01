package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
