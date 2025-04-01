package com.shopix.api.entities;

import java.sql.Date;
import java.util.List;

import com.shopix.api.enuns.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private float totalPrice;
	private OrderStatus status;
	private String paymentMethod;
	private String trackingCode;
	private Date created_at;
	private Date updated_at;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@OneToMany
	@JoinColumn(name="order_id")
	private List<OrderItem> items;

}
