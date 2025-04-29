package com.shopix.api.dtos;

import java.time.Instant;
import java.util.List;

import com.shopix.api.enuns.OrderStatus;

public record OrderResponseDTO(
	Long id,
	float totalPrice,
	OrderStatus status,
	String paymentMethod,
	String trackingCode,
	Instant created_at,
	Instant updated_at,
	List<OrderItemResponseDTO> items) { }
