package com.shopix.api.dtos;

public record OrderItemResponseDTO(
	Long id,
	int quantity,
	float subtotal,
	Long product_variation_id) { }
