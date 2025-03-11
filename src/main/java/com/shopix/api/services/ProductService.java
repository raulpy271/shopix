package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shopix.api.entities.Product;
import com.shopix.api.repository.ProductRepository;

public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public void store(Product product)
	{
		productRepository.save(product);
	}
	
	public List<Product> list()
	{
		return productRepository.findAll();
	}
}