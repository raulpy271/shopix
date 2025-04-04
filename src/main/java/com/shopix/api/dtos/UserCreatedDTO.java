package com.shopix.api.dtos;

import com.shopix.api.enuns.Role;

public record UserCreatedDTO(
	String username,
	String fullname,
	String email,
	Role role) { }