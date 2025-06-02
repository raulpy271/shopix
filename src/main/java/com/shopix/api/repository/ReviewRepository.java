package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@NativeQuery("SELECT rv.* FROM reviews rv WHERE rv.product_variation_id in :ids")
	List<Review> findByProductVariationIdIn(@Param("ids") List<Long> ids);
}
