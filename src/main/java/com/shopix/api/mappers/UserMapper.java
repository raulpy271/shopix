package com.shopix.api.mappers;

import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.entities.User;

public class UserMapper {
	public static UserResponseDTO toDTO(User user)
	{
		return new UserResponseDTO(user.getId(), user.getUsername(), user.getFullname(), user.getEmail(), user.getRole(), user.isAdmin(), user.getCreated_at(), user.getUpdated_at());
	}
	
	public static User toEntity(UserCreatedDTO userCreated)
	{
		User user = new User();
		user.setUsername(userCreated.username());
		user.setFullname(userCreated.fullname());
		user.setEmail(userCreated.email());
		user.setRole(userCreated.role());
		return user;
	}

}
