package com.shopix.api.dtos;

import java.time.Instant;
import java.util.List;

public record ProductResponseDTO(
	Long id,
	String name,
	double price,
	int stock,
	String category,
	String brand,
	float rating,
	Instant created_at,
	Instant updated_at,
	List<ProductVariationResponseDTO> vars) { }
