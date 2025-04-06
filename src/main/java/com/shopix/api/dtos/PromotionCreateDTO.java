package com.shopix.api.dtos;

import java.sql.Date;

public record PromotionCreateDTO(
	String name,
	float discountPercentage,
	Date startDate,
	Date endDate,
	boolean isActive) { }
