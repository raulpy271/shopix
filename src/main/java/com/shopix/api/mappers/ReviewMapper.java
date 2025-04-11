package com.shopix.api.mappers;

import com.shopix.api.dtos.ReviewCreateDTO;
import com.shopix.api.dtos.ReviewResponseDTO;
import com.shopix.api.entities.Review;

public class ReviewMapper {
	public static ReviewResponseDTO toDTO(Review review)
	{
		return new ReviewResponseDTO(review.getId(), review.getRating(), review.getComment(), review.getCreated_at(), review.getUpdated_at());
	}
	
	public static Review toEntity(ReviewCreateDTO dto)
	{
		Review review = new Review();
		review.setRating(dto.rating());
		review.setComment(dto.comment());
		return review;
	}
}
