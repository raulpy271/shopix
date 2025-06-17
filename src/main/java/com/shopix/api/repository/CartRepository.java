package com.shopix.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@NativeQuery("SELECT ci.* FROM cart_items ci WHERE ci.cart_id = :id")
	List<CartItem> listCartItems(@Param("id") Long id);
	
	@NativeQuery("SELECT ci.* FROM cart_items ci WHERE ci.id = :id")
	CartItem getCartItemById(@Param("id") Long id);

	@NativeQuery("SELECT c.* FROM carts c WHERE c.user_id = :id")
	Cart getCartByUserId(@Param("id") Long id);
}
