package com.shopix.api.dtos;

import java.util.List;

public record ProductCreateDTO(
	String name,
	double price,
	int stock,
	String category,
	String brand,
	float rating,
	List<ProductVariationCreateDTO> vars) { }
