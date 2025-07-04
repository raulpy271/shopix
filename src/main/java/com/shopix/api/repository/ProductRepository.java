package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Product;
import com.shopix.api.entities.ProductVariation;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@NativeQuery("SELECT pv.* FROM product_variations pv WHERE pv.id = :id")
	ProductVariation getProductVariationById(@Param("id") Long id);
	
	@NativeQuery("SELECT pv.* FROM product_variations pv WHERE pv.product_id = :product_id")
	List<ProductVariation> getProductVariationsByProductId(@Param("product_id") Long id);
}
