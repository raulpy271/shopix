package com.shopix.api.dtos;

import java.util.Optional;

public record BuyItemDTO(
	Optional<Long> promotion_id,
	Long cart_item_id) { }
