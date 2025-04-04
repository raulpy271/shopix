package com.shopix.api.dtos;

import java.util.HashMap;

public record ProductVariationResponseDTO(
	Long id,
	HashMap<String, String> options,
	int stock) { }
