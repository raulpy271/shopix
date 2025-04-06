package com.shopix.api.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name="carts")
@Table(name="carts")
@Getter
@Setter
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany
	@JoinColumn(name="cart_id")
	private List<CartItem> carts;
}