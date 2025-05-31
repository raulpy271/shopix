package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.resources.payment.Payment;
import com.shopix.api.dtos.MPPaymentDTO;
import com.shopix.api.dtos.PaymentCreateDTO;
import com.shopix.api.dtos.PaymentResponseDTO;
import com.shopix.api.dtos.PaymentUpdateDTO;
import com.shopix.api.services.PaymentService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	@Autowired
	PaymentService paymentService;

	@Value("${mp.key}") private String key;
       
	@PostConstruct
	public void init() {
		MercadoPagoConfig.setAccessToken(key);
	}

	@PostMapping
	public ResponseEntity<PaymentResponseDTO> create(@RequestBody PaymentCreateDTO dto)
	{
		return new ResponseEntity<>(paymentService.store(dto), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<PaymentResponseDTO>> list()
	{
		return new ResponseEntity<>(paymentService.list(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PaymentResponseDTO> show(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(paymentService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping
	public ResponseEntity<PaymentResponseDTO> update(@RequestBody PaymentUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(paymentService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> destroy(@PathVariable Long id)
	{
		try {
			paymentService.destroy(id);
			return new ResponseEntity<>("Pagamento deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/pay")
	public ResponseEntity<?> processPayment(@RequestBody MPPaymentDTO dto) {
		try {
			PaymentClient client = new PaymentClient();
			PaymentCreateRequest req = PaymentCreateRequest
				.builder()
				.transactionAmount(dto.amount())
				.token(dto.token())
				.description(dto.description())
				.installments(dto.installments())
				.paymentMethodId(dto.paymentMethodId())
				.payer(
					PaymentPayerRequest.builder()
					.email(dto.email())
					.identification(
						   IdentificationRequest.builder()
						   .type("CPF")
						   .number(dto.cpf())
						   .build()
				).build()
			).build();
			Payment payment = client.create(req);
			return ResponseEntity.status(HttpStatus.OK).body(payment);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
