package com.shopix.api.dtos;

import java.util.HashMap;

public record ProductVariationCreateDTO(
	HashMap<String, String> options,
	int stock) { }
