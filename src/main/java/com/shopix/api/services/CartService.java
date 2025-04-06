package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.dtos.CartUpdateDTO;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.mappers.CartMapper;
import com.shopix.api.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;
	
	public CartResponseDTO store(CartCreateDTO dto)
	{
		Cart create = CartMapper.toEntity(dto);
		return CartMapper.toDTO(cartRepository.save(create));
	}
	
	public CartResponseDTO show(Long id)
	{
		Cart cart = cartRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
		return CartMapper.toDTO(cart);
	}
	
	public List<CartResponseDTO> list()
	{
		List<Cart> carts = cartRepository.findAll();
		return carts.stream().map(CartMapper::toDTO).toList();
	}
	
	public CartResponseDTO update(CartUpdateDTO dto)
	{
		Cart cart = cartRepository
			.findById(dto.id())
			.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
		cart.setUser(dto.user());
		return CartMapper.toDTO(cartRepository.save(cart));
	}
	
	public void destroy(Long id)
	{
		Cart cart = cartRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
		cartRepository.delete(cart);
	}
}
