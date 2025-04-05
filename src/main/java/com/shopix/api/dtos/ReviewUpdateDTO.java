package com.shopix.api.dtos;

public record ReviewUpdateDTO(
	Long id,
	int rating,
	String comment){ }
