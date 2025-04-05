package com.shopix.api.dtos;

import java.time.Instant;

public record ReviewResponseDTO(
	Long id,
	int rating,
	String comment,
	Instant created_at,
	Instant updated_at) { }
