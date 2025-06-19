package com.shopix.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="cart_items")
@Table(name="cart_items")
@Getter
@Setter
public class CartItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private int quantity;
	private float subtotal;


	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;

	@ManyToOne
	@JoinColumn(name="product_variation_id")
	private ProductVariation var;

	@ManyToOne
	@JoinColumn(name="promotion_id")
	private Promotion promotion;

}
