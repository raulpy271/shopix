package com.shopix.api.mappers;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.entities.Review;
import com.shopix.api.repository.ProductRepository;

public class ReviewMapper {
	public static ReviewResponseDTO toDTO(Review review)
	{
		UserResponseDTO dto = UserMapper.toDTO(review.getUser());
		return new ReviewResponseDTO(review.getId(), review.getRating(), review.getComment(), review.getCreated_at(), review.getUpdated_at(), dto);
	}
	
	public static Review toEntity(ReviewCreateDTO dto, ProductRepository productRepository)
	{
		Review review = new Review();
		review.setRating(dto.rating());
		review.setComment(dto.comment());
		review.setVar(productRepository.getProductVariationById(dto.var_id()));
		return review;
	}
}
