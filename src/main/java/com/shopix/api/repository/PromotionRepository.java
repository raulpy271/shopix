package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
