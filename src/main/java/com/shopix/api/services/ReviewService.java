package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.dtos.ReviewUpdateDTO;
import com.shopix.api.entities.Review;
import com.shopix.api.mappers.ReviewMapper;
import com.shopix.api.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	ReviewRepository reviewRepository;
	
	public ReviewResponseDTO store(ReviewCreateDTO dto)
	{
		Review review = ReviewMapper.toEntity(dto);
		return ReviewMapper.toDTO(reviewRepository.save(review));
	}
	
	public List<ReviewResponseDTO> list()
	{
		List<Review> reviews = reviewRepository.findAll();
		return reviews.stream().map(ReviewMapper::toDTO).toList();
	}
	
	public ReviewResponseDTO show(Long id)
	{
		Review review = reviewRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Review não encontrada"));
		return ReviewMapper.toDTO(review);
	}
	
	public ReviewResponseDTO update(ReviewUpdateDTO dto)
	{
		Review review = reviewRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Review não encontrada"));
		review.setRating(dto.rating());
		review.setComment(dto.comment());
		return ReviewMapper.toDTO(reviewRepository.save(review));
	}
	
	public void destroy(Long id)
	{
		Review review = reviewRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Review não encontrada"));
		reviewRepository.delete(review);
	}

}
