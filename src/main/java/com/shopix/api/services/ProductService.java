package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.dtos.ProductUpdateDTO;
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
	
	public ProductResponseDTO update(ProductUpdateDTO dto)
	{
		Product product = productRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		product.setName(dto.name());
		product.setPrice(dto.price());
		product.setStock(dto.stock());
		product.setRating(dto.rating());
		product.setBrand(dto.brand());
		product.setCategory(dto.category());
		return ProductMapper.toDTO(productRepository.save(product));
	}
	
	public void destroy(Long id)
	{
		Product product = productRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		productRepository.delete(product);
	}
}