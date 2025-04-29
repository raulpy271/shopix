package com.shopix.api.mappers;

import com.shopix.api.dtos.OrderItemCreateDTO;
import com.shopix.api.dtos.OrderItemResponseDTO;
import com.shopix.api.entities.OrderItem;
import com.shopix.api.repository.ProductRepository;

public class OrderItemMapper {
	
	public static OrderItemResponseDTO toDTO(OrderItem item)
	{
		Long var = null;
		if (item.getVar() != null) {
			var = item.getVar().getId();
		} else {
			var = 0L;
		}
		return new OrderItemResponseDTO(item.getId(), item.getQuantity(), item.getSubtotal(), var);
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
