package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	//@Query("SELECT ci FROM cart_items ci WHERE ci.cart_id = ?1")
	//List<CartItem> listCartItems(Long id);
}
