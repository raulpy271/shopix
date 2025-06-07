package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	@NativeQuery("SELECT p.* FROM promotions p WHERE p.product_id = :product_id")
	List<Promotion> getPromotionsByProductId(@Param("product_id") Long id);

}
