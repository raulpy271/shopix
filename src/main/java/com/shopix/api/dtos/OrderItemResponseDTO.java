package com.shopix.api.dtos;

public record OrderItemResponseDTO(
	Long id,
	int quantity,
	float subtotal,
	ProductVariationResponseDTO var
	) { }
