package com.shopix.api.dtos;

import java.sql.Date;
import java.util.List;

import com.shopix.api.enuns.OrderStatus;

public record OrderResponseDTO(
	Long id,
	float totalPrice,
	OrderStatus status,
	String paymentMethod,
	String trackingCode,
	Date created_at,
	Date updated_at,
	List<OrderItemResponseDTO> items) { }
