package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.ProductVariation;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {

}
