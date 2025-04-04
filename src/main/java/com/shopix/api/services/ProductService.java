package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.mappers.ProductMapper;
import com.shopix.api.repository.ProductRepository;

@Component
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ProductResponseDTO store(ProductCreateDTO dto)
	{
		Product product = productRepository.save(ProductMapper.toEntity(dto));
		return ProductMapper.toDTO(product);
	}
	
	public ProductResponseDTO show(Long id)
	{
		Product product = productRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		return ProductMapper.toDTO(product);
	}
	
	public List<ProductResponseDTO> list()
	{
		List<Product> ps = productRepository.findAll();
		return ps.stream().map(ProductMapper::toDTO).toList();

	}
}