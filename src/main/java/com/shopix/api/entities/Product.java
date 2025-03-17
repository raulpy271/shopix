package com.shopix.api.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Setter
@Getter
@NoArgsConstructor
public class Product implements Serializable {
	private @Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	private String name;
	private double price;
	private int stock;
}
