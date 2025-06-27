package com.shopix.api.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.dtos.ReviewUpdateDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.Review;
import com.shopix.api.entities.User;
import com.shopix.api.mappers.ReviewMapper;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.ReviewRepository;
import com.shopix.api.repository.UserRepository;

@Service
public class ReviewService {
	@Autowired
	ReviewRepository reviewRepository;
	@Autowired
	ProductRepository productRepository;
	
	public ReviewResponseDTO store(Authentication auth, ReviewCreateDTO dto)
	{
		User user = (User) auth.getPrincipal();
		Review review = ReviewMapper.toEntity(dto, productRepository);
		review.setUser(user);
		review = reviewRepository.save(review);
		Product product = review.getVar().getProduct();
		product.setRating(this.calculateRating(product.getId()));
		productRepository.save(product);
		return ReviewMapper.toDTO(review);
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
	
	private float calculateRating(Long product_id) {
		float sum = 0;
		List<Long> pvar_ids = productRepository
			.getProductVariationsByProductId(product_id)
			.stream()
			.map(var -> var.getId())
			.toList();
		List<Review> allReviews = reviewRepository.findByProductVariationIdIn(pvar_ids);
		for (Review r : allReviews) {
			sum += (float) r.getRating();
		}
		return sum / allReviews.size();
	}

}
