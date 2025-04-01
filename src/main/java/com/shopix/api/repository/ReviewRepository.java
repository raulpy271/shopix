package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopix.api.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
