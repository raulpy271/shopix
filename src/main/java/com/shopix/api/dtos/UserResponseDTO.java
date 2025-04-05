package com.shopix.api.dtos;

import java.time.Instant;

import com.shopix.api.enuns.Role;

public record UserResponseDTO(
	Long id,
	String username,
	String fullname,
	String email,
	Role role,
	boolean admin,
	Instant created_at,
	Instant updated_at) { }
