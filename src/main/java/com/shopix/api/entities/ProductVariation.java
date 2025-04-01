package com.shopix.api.entities;

import java.util.HashMap;

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
@Table(name="product_variations")
@Getter
@Setter
public class ProductVariation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private HashMap<String, String> options;
	private int stock;

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
}
