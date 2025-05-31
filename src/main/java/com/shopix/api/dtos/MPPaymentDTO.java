package com.shopix.api.dtos;

import java.math.BigDecimal;

public record MPPaymentDTO(
		String token,
		BigDecimal amount,
		String description,
		int installments,
		String paymentMethodId,
		String email,
		String cpf
	) { }
