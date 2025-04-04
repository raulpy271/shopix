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

import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.dtos.ProductUpdateDTO;
import com.shopix.api.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductCreateDTO dto)
	{
		ProductResponseDTO created = productService.store(dto);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> show(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(productService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> list() 
	{
		return new ResponseEntity<>(productService.list(), HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductUpdateDTO dto)
	{
		try {
			return new ResponseEntity<>(productService.update(dto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> destroy(@PathVariable Long id)
	{
		try {
			productService.destroy(id);
			return new ResponseEntity<>("Produto deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
