package com.shopix.api.dtos;

public record CartItemCreateDTO(
	int quantity,
	float subtotal,
	Long product_variation_id,
	Long promotion_id) { }
