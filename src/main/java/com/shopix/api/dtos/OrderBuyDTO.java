package com.shopix.api.dtos;

import java.util.List;

public record OrderBuyDTO(
	String paymentMethod,
	Long address_id,
	List<BuyItemDTO> items) {}
