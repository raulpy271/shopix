package com.shopix.api.dtos;

import java.util.List;

public record OrderBuyDTO(
	String address,
	String paymentMethod,
	List<BuyItemDTO> items) {}
