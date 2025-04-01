package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
