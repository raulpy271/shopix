package com.shopix.api.dtos;

public record CartItemResponseDTO(
	Long id,
	int quantity,
	float subtotal,
	Long promotion_id,
	ProductVariationResponseDTO var) { }
