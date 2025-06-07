package com.shopix.api.entities;

import java.sql.Date;

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
@Table(name="promotions")
@Getter
@Setter
public class Promotion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private float discountPercentage;
	private Date startDate;
	private Date endDate;
	private boolean isActive;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
