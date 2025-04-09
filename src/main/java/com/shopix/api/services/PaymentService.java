package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.PaymentCreateDTO;
import com.shopix.api.dtos.PaymentResponseDTO;
import com.shopix.api.dtos.PaymentUpdateDTO;
import com.shopix.api.entities.Payment;
import com.shopix.api.mappers.PaymentMapper;
import com.shopix.api.repository.PaymentRepository;

@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	
	public PaymentResponseDTO store(PaymentCreateDTO dto)
	{
		Payment payment = PaymentMapper.toEntity(dto);
		return PaymentMapper.toDTO(paymentRepository.save(payment));
	}
	
	public List<PaymentResponseDTO> list()
	{
		List<Payment> payments = paymentRepository.findAll();
		return payments.stream().map(PaymentMapper::toDTO).toList();
	}
	
	public PaymentResponseDTO show(Long id)
	{
		Payment payment = paymentRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
		return PaymentMapper.toDTO(payment);
	}
	
	public PaymentResponseDTO update(PaymentUpdateDTO dto)
	{
		Payment payment = paymentRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
		payment.setAmount(dto.amount());
		payment.setStatus(dto.status());
		payment.setMethod(dto.method());
		return PaymentMapper.toDTO(payment);
	}
	
	public void destroy(Long id)
	{
		Payment payment = paymentRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
		paymentRepository.delete(payment);
	}

}
