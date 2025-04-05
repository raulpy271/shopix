package com.shopix.api.dtos;

import com.shopix.api.entities.User;

public record CartUpdateDTO(
	Long id,
	User user) { }
