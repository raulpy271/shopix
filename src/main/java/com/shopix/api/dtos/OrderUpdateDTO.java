package com.shopix.api.dtos;

import com.shopix.api.enuns.OrderStatus;

public record OrderUpdateDTO(
	Long id,
	float totalPrice,
	OrderStatus status,
	String paymentMethod,
	String trackingCode) { }
