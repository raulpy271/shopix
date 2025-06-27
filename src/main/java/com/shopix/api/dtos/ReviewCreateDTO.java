package com.shopix.api.dtos;

public record ReviewCreateDTO(
	int rating,
	String comment,
	Long var_id) { }
