package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopix.api.entities.Product;
import com.shopix.api.repository.ProductRepository;

@Component
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product store(Product product)
	{
		return productRepository.save(product);
	}
	
	public Product findById(Long id)
	{
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
	}
	
	public List<Product> list()
	{
		return productRepository.findAll();
	}
}