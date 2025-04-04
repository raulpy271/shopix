package com.shopix.api.dtos;

public record ProductUpdateDTO(
	Long id,
	String name,
	double price,
	int stock,
	String category,
	String brand,
	float rating) {}
