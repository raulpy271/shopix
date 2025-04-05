package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;

public interface CartRepository extends JpaRepository<Cart, Long> {
	@Query("SELECT cart_items.* FROM cart_items"
		+ " JOIN carts ON carts.id = cart_items.cart_id WHERE carts.id = ?")
	public List<CartItem> listCartItems(Long id);

}
