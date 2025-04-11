package com.shopix.api.mappers;

import com.shopix.api.dtos.ProductVariationCreateDTO;
import com.shopix.api.dtos.ProductVariationResponseDTO;
import com.shopix.api.entities.ProductVariation;

public class ProductVariationMapper {

	public static ProductVariationResponseDTO toDTO(ProductVariation var)
	{
		return new ProductVariationResponseDTO(var.getId(), var.getOptions(), var.getStock());
	}
	
	public static ProductVariation toEntity(ProductVariationCreateDTO dto)
	{
		ProductVariation var = new ProductVariation();
		var.setOptions(dto.options());
		var.setStock(dto.stock());
		return var;
	}
}
