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

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartItemCreateDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.dtos.CartUpdateDTO;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.User;
import com.shopix.api.services.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {
	@Autowired
	CartService cartService;
	
	@PostMapping
	public ResponseEntity<CartResponseDTO> create(@RequestBody CartCreateDTO dto)
	{
		return new ResponseEntity<>(cartService.store(dto), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CartResponseDTO> show(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(cartService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<CartResponseDTO>> list()
	{
		return new ResponseEntity<>(cartService.list(), HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<CartResponseDTO> update(@RequestBody CartUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(cartService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> destroy(@PathVariable Long id)
	{
		try {
			cartService.destroy(id);
			return new ResponseEntity<>("Carrinho deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addItem")
	public ResponseEntity<CartResponseDTO> addItem(Authentication auth, @RequestBody CartItemCreateDTO dto)
	{
		try {
			User user = (User) auth.getPrincipal();
			CartResponseDTO cart = cartService.getUserCart(user);
			return new ResponseEntity<>(cartService.addItem(cart.id(), dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/removeItem/{cart_id}/{item_id}")
	public ResponseEntity<CartResponseDTO> addItem(@PathVariable Long cart_id, @PathVariable Long item_id)
	{
		try {
			return new ResponseEntity<>(cartService.removeItem(cart_id, item_id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/my")
	public ResponseEntity<CartResponseDTO> myCart(Authentication auth)
	{
		try {
			User user = (User) auth.getPrincipal();
			CartResponseDTO cart = cartService.getUserCart(user);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
