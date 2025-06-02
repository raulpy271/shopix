package com.shopix.api.dtos;

public record ReviewCreateDTO(
	int rating,
	String comment,
	Long var_id,
	Long user_id) { }
