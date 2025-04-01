package com.shopix.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="order_items")
@Getter
@Setter
public class OrderItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private int quantity;
	private float subtotal;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_variation_id")
	private ProductVariation var;



}
