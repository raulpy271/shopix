package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.dtos.OrderBuyDTO;
import com.shopix.api.dtos.OrderCreateDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.dtos.OrderUpdateDTO;
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

	@GetMapping("/my")
	public ResponseEntity<List<OrderResponseDTO>> listMyOrders(Authentication auth)
	{
		return new ResponseEntity<>(orderService.listMyOrders(auth), HttpStatus.OK);
	}

	@PostMapping("/buy")
	public ResponseEntity<OrderResponseDTO> buy(Authentication auth, @RequestBody OrderBuyDTO dto)
	{
		try {
			return new ResponseEntity<>(orderService.buy(auth, dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDTO> show(@PathVariable Long id)
	{

		try {
			OrderResponseDTO dto =  orderService.show(id);
			return new ResponseEntity(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping
	public ResponseEntity<OrderResponseDTO> update(@RequestBody OrderUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(orderService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> destroy(@PathVariable Long id)
	{
		try {
			orderService.destroy(id);
			return new ResponseEntity<>("Ordem deletada com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
