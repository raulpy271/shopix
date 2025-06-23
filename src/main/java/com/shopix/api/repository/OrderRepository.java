package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	@NativeQuery("SELECT o.* FROM orders o WHERE o.user_id = :id")
	List<Order> getOrdersByUserId(@Param("id") Long id);
}
