package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartItemCreateDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.dtos.CartUpdateDTO;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.mappers.CartItemMapper;
import com.shopix.api.mappers.CartMapper;
import com.shopix.api.repository.CartRepository;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.UserRepository;

@Service
public class CartService {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	
	public CartResponseDTO store(CartCreateDTO dto)
	{
		Cart create = CartMapper.toEntity(dto, userRepository);
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
	
	public CartResponseDTO addItem(Long id, CartItemCreateDTO dto)
	{
		Cart cart = cartRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
		CartItem item = CartItemMapper.toEntity(dto, productRepository);
		cart.getItems().add(item);
		return CartMapper.toDTO(cartRepository.save(cart));
	}
	
	public CartResponseDTO removeItem(Long cart_id, Long item_id)
	{
		Cart cart = cartRepository
			.findById(cart_id)
			.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
		CartItem item = cartRepository.getCartItemById(item_id);
		cart.getItems().remove(item);
		return CartMapper.toDTO(cartRepository.save(cart));
	}
}
