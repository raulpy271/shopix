package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
