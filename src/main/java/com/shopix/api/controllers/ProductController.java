package com.shopix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.dtos.ProductDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.mappers.ProductMapper;
import com.shopix.api.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO p)
	{
		Product created = productService.store(ProductMapper.toEntity(p));
		return new ResponseEntity<>(ProductMapper.toDTO(created), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ProductDTO get(@PathVariable Long id)
	{
		Product product = productService.findById(id);
		return ProductMapper.toDTO(product);
	}

	@GetMapping
	public List<Product> list() 
	{
		return productService.list();
	}
}
