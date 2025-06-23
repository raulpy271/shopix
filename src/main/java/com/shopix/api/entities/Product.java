package com.shopix.api.entities;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="products")
@Setter
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private double price;
	private int stock;
	private String category;
	private String brand;
	private float rating;
	@CreatedDate
	private Instant created_at;
	@LastModifiedBy
	private Instant updated_at;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="product_id")
	List<ProductVariation> vars;
}
