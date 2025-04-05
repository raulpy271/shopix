package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
