package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.dtos.ReviewUpdateDTO;
import com.shopix.api.entities.Review;
import com.shopix.api.mappers.ReviewMapper;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.ReviewRepository;
import com.shopix.api.repository.UserRepository;

@Service
public class ReviewService {
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	
	public ReviewResponseDTO store(ReviewCreateDTO dto)
	{
		Review review = ReviewMapper.toEntity(dto, productRepository, userRepository);
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
	
	public List<ReviewResponseDTO> productReviews(Long product_id) {
		List<Long> pvar_ids = productRepository
			.getProductVariationsByProductId(product_id)
			.stream()
			.map(var -> var.getId())
			.toList();
		List<Review> reviews = reviewRepository.findByProductVariationIdIn(pvar_ids);
		return reviews.stream().map(ReviewMapper::toDTO).toList();
	}

}
