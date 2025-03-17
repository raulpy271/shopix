package com.shopix.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.entities.Product;
import com.shopix.api.services.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("product")
	public void create(@RequestBody Product p)
	{
		productService.store(p);
	}

	@GetMapping("/product/{id}")
	public Product get(@PathVariable Long id)
	{
		return productService.findById(id);
	}

	@GetMapping("/products")
	public List<Product> list() 
	{
		return productService.list();
	}
}
