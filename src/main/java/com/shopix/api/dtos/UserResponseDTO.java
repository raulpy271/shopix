package com.shopix.api.dtos;

import java.sql.Date;

import com.shopix.api.enuns.Role;

public record UserResponseDTO(
	Long id,
	String username,
	String fullname,
	String email,
	Role role,
	boolean admin,
	Date created_at,
	Date updated_at) { }
