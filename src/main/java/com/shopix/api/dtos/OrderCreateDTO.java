package com.shopix.api.dtos;

import java.util.List;

import com.shopix.api.enuns.OrderStatus;

public record OrderCreateDTO(
	float totalPrice,
	OrderStatus status,
	String paymentMethod,
	String trackingCode,
	List<OrderItemCreateDTO> items) { }
