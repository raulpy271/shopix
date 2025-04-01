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
@Table(name="reviews")
@Getter
@Setter
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private int rating;
	private String comment;
	private Date created_at;
	private Date updated_at;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="product_variation_id")
	private ProductVariation var;
}
