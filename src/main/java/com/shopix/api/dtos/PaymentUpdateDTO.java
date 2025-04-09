package com.shopix.api.dtos;

import com.shopix.api.enuns.PaymentMethod;
import com.shopix.api.enuns.PaymentStatus;

public record PaymentUpdateDTO(
	Long id,
	float amount,
	PaymentStatus status,
	PaymentMethod method) { }
