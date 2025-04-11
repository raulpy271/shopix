package com.shopix.api.mappers;

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.entities.Cart;

public class CartMapper {

	public static CartResponseDTO toDTO(Cart cart)
	{
		return new CartResponseDTO(cart.getId(), cart.getUser());
	}
	
	public static Cart toEntity(CartCreateDTO dto)
	{
		Cart cart = new Cart();
		cart.setUser(dto.user());
		return cart;
	}
}
