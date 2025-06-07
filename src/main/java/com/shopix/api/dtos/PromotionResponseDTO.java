package com.shopix.api.dtos;

import java.sql.Date;

public record PromotionResponseDTO(
	Long id,
	String name,
	float discountPercentage,
	Date startDate,
	Date endDate,
	boolean isActive,
	Long product_id) { }
