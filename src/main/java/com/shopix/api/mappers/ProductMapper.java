package com.shopix.api.mappers;

import java.util.List;

import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.dtos.ProductVariationResponseDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.ProductVariation;

public class ProductMapper {
	public static ProductResponseDTO toDTO(Product product)
	{
		List<ProductVariationResponseDTO> vars;
		if (product.getVars() != null) {
			vars = product.getVars().stream().map(ProductVariationMapper::toDTO).toList();
		} else {
			vars = List.of();
		}
		return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCategory(), product.getBrand(), product.getRating(), product.getCreated_at(), product.getUpdated_at(), vars);
	}
	
	public static Product toEntity(ProductCreateDTO dto)
	{
		List<ProductVariation> vars = dto.vars().stream().map(ProductVariationMapper::toEntity).toList();
		Product p = new Product();
		p.setName(dto.name());
		p.setPrice(dto.price());
		p.setStock(dto.stock());
		p.setCategory(dto.category());
		p.setBrand(dto.brand());
		p.setRating(dto.rating());
		p.setVars(vars);
		return p;
	}
}
