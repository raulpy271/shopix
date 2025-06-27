package com.shopix.api.dtos;

public record AddressCreateDTO(
	String street,
	String number,
	String neighborhood,
	String city,
	String complement,
	String state,
	Long zipCode) { }
