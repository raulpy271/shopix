package com.shopix.api.dtos;

import com.shopix.api.enuns.Role;

public record UserUpdateDTO(
	Long id,
	String username,
	String fullname,
	String email,
	Role role) { }
