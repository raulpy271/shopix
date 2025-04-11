package com.shopix.api.mappers;

import com.shopix.api.dtos.PaymentCreateDTO;
import com.shopix.api.dtos.PaymentResponseDTO;
import com.shopix.api.entities.Payment;

public class PaymentMapper {
	public static PaymentResponseDTO toDTO(Payment payment)
	{
		return new PaymentResponseDTO(payment.getId(), payment.getAmount(), payment.getStatus(), payment.getMethod(), payment.getCreated_at(), payment.getUpdated_at());
	}

	public static Payment toEntity(PaymentCreateDTO dto)
	{
		Payment payment = new Payment();
		payment.setAmount(dto.amount());
		payment.setStatus(dto.status());
		payment.setMethod(dto.method());
		return payment;
	}
}
