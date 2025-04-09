package com.shopix.api.entities;

import java.time.Instant;

import com.shopix.api.enuns.PaymentMethod;
import com.shopix.api.enuns.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="payments")
@Getter
@Setter
public class Payment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name="order_id")
	private Order order;
	private float amount;
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	@Enumerated(EnumType.STRING)
	private PaymentMethod method;
	private Instant created_at;
	private Instant updated_at;

}
