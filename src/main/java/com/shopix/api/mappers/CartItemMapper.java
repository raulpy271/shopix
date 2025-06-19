package com.shopix.api.mappers;

import com.shopix.api.dtos.CartItemCreateDTO;
import com.shopix.api.dtos.CartItemResponseDTO;
import com.shopix.api.entities.CartItem;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.PromotionRepository;

public class CartItemMapper {
	public static CartItemResponseDTO toDTO(CartItem cart)
	{
		Long promotion_id = (cart.getPromotion() != null) ? cart.getPromotion().getId() : null;
		return new CartItemResponseDTO(cart.getId(), cart.getQuantity(), cart.getSubtotal(), promotion_id, ProductVariationMapper.toDTO(cart.getVar()));
	}
	
	public static CartItem toEntity(CartItemCreateDTO dto, ProductRepository productRepository, PromotionRepository promotionRepository)
	{
		CartItem item = new CartItem();
		item.setQuantity(dto.quantity());
		item.setSubtotal(dto.subtotal());
		if (dto.promotion_id() != null) {
			item.setPromotion(promotionRepository.findById(dto.promotion_id()).get());
		}
		item.setVar(
			productRepository.getProductVariationById(dto.product_variation_id())
		);
		return item;
	}

}
