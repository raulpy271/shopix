package com.shopix.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
