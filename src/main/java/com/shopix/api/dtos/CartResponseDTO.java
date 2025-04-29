package com.shopix.api.dtos;

import java.util.List;

public record CartResponseDTO(
	Long id,
	Long user_id,
	List<CartItemResponseDTO> items) { }
