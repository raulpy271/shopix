package com.shopix.api.dtos;

import java.time.Instant;

import com.shopix.api.enuns.PaymentMethod;
import com.shopix.api.enuns.PaymentStatus;

public record PaymentResponseDTO(
	Long id,
	float amount,
	PaymentStatus status,
	PaymentMethod method,
	Instant created_at,
	Instant updated_at) { }
