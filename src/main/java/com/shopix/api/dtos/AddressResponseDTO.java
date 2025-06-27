package com.shopix.api.dtos;

public record AddressResponseDTO(
	Long id,
	String street,
	String number,
	String neighborhood,
	String city,
	String complement,
	String state,
	Long zipCode) { }
