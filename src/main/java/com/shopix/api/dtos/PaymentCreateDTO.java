package com.shopix.api.dtos;

import com.shopix.api.enuns.PaymentMethod;
import com.shopix.api.enuns.PaymentStatus;

public record PaymentCreateDTO(
	float amount,
	PaymentStatus status,
	PaymentMethod method) { }
