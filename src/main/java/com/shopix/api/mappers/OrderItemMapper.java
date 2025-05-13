package com.shopix.api.mappers;

import com.shopix.api.dtos.OrderItemCreateDTO;
import com.shopix.api.dtos.OrderItemResponseDTO;
import com.shopix.api.dtos.ProductVariationResponseDTO;
import com.shopix.api.entities.OrderItem;
import com.shopix.api.entities.ProductVariation;
import com.shopix.api.repository.ProductRepository;

public class OrderItemMapper {
	
	public static OrderItemResponseDTO toDTO(OrderItem item)
	{
		ProductVariationResponseDTO varDTO = null;
		if (item.getVar() != null) {
			varDTO = ProductVariationMapper.toDTO(item.getVar());
		}
		return new OrderItemResponseDTO(item.getId(), item.getQuantity(), item.getSubtotal(), varDTO);
	}
	
	public static OrderItem toEntity(OrderItemCreateDTO dto, ProductRepository productRepository)
	{
		OrderItem item = new OrderItem();
		item.setQuantity(dto.quantity());
		item.setSubtotal(dto.subtotal());
		item.setVar(
			productRepository.getProductVariationById(dto.product_variation_id())
		);
		return item;
	}

}
