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
@Table(name="addresses")
@Getter
@Setter
public class Address {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String street;
	private String number;
	private String neighborhood;
	private String city;
	private String complement;
	private String state;
	private Long zipCode;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
}
