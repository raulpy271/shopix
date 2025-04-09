package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.shopix.api.dtos.PaymentCreateDTO;
import com.shopix.api.dtos.PaymentResponseDTO;
import com.shopix.api.dtos.PaymentUpdateDTO;
import com.shopix.api.services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	@Autowired
	PaymentService paymentService;

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

}
