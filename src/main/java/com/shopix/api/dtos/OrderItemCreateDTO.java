package com.shopix.api.dtos;

public record OrderItemCreateDTO(
	int quantity,
	float subtotal,
	Long product_variation_id) { }
