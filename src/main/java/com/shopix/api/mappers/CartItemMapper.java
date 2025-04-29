package com.shopix.api.mappers;

import com.shopix.api.dtos.CartItemCreateDTO;
import com.shopix.api.dtos.CartItemResponseDTO;
import com.shopix.api.entities.CartItem;
import com.shopix.api.repository.ProductRepository;

public class CartItemMapper {
	public static CartItemResponseDTO toDTO(CartItem cart)
	{
		return new CartItemResponseDTO(cart.getId(), cart.getQuantity(), cart.getSubtotal(), cart.getVar().getId());
	}
	
	public static CartItem toEntity(CartItemCreateDTO dto, ProductRepository productRepository)
	{
		CartItem item = new CartItem();
		item.setQuantity(dto.quantity());
		item.setSubtotal(dto.subtotal());
		item.setVar(
			productRepository.getProductVariationById(dto.product_variation_id())
		);
		return item;
	}

}
