package com.shopix.api.mappers;

import com.shopix.api.dtos.ProductVariationCreateDTO;
import com.shopix.api.dtos.ProductVariationResponseDTO;
import com.shopix.api.entities.ProductVariation;

public class ProductVariationMapper {

	public static ProductVariationResponseDTO toDTO(ProductVariation var)
	{
		Long product_id = null;
		if (var.getProduct() != null) {
			product_id = var.getProduct().getId();
		}
		return new ProductVariationResponseDTO(var.getId(), var.getOptions(), var.getStock(), product_id);
	}
	
	public static ProductVariation toEntity(ProductVariationCreateDTO dto)
	{
		ProductVariation var = new ProductVariation();
		var.setOptions(dto.options());
		var.setStock(dto.stock());
		return var;
	}
}
