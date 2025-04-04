package com.shopix.api.dtos;

import java.sql.Date;
import java.util.List;

public record ProductResponseDTO(
	Long id,
	String name,
	double price,
	int stock,
	String category,
	String brand,
	float rating,
	Date created_at,
	Date updated_at,
	List<ProductVariationResponseDTO> vars) { }
