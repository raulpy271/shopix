package com.shopix.api.mappers;

import java.util.List;

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartItemResponseDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.entities.Cart;
import com.shopix.api.repository.UserRepository;

public class CartMapper {

	public static CartResponseDTO toDTO(Cart cart)
	{
		List<CartItemResponseDTO> items;
		if (cart.getItems() != null && cart.getItems().size() > 0) {
			items = cart.getItems().stream().map(CartItemMapper::toDTO).toList();
		} else {
			items = List.of();
		}
		return new CartResponseDTO(cart.getId(), cart.getUser().getId(), items);
	}
	
	public static Cart toEntity(CartCreateDTO dto, UserRepository userRepository)
	{
		Cart cart = new Cart();
		cart.setUser(
			userRepository
			.findById(dto.user_id())
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
		);
		return cart;
	}
}
