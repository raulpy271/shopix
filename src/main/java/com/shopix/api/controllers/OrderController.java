package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	@Autowired
	OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponseDTO> store(@RequestBody OrderCreateDTO order)
	{
		OrderResponseDTO created = orderService.store(order);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<OrderResponseDTO>> list()
	{
		return new ResponseEntity<>(orderService.list(), HttpStatus.OK);
	}

}
