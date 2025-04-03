package com.shopix.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.dtos.UserUpdateDTO;
import com.shopix.api.entities.User;
import com.shopix.api.mappers.UserMapper;
import com.shopix.api.repository.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserResponseDTO store(UserCreatedDTO created)
	{
		User user = UserMapper.toEntity(created);
		return UserMapper.toDTO(userRepository.save(user));
	}
	
	public List<UserResponseDTO> list()
	{
		List<User> users = userRepository.findAll();
		return users.stream().map(UserMapper::toDTO).toList();
	}
	
	public UserResponseDTO show(long id)
	{
		User user = userRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		return UserMapper.toDTO(user);
	}
	
	public UserResponseDTO update(UserUpdateDTO update)
	{
		User user = userRepository
			.findById(update.id())
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		user.setUsername(update.username());
		user.setFullname(update.fullname());
		user.setEmail(update.email());
		user.setRole(update.role());
		return UserMapper.toDTO(userRepository.save(user));
	}
	
	public void destroy(long id)
	{
		User user = userRepository
			.findById(id)
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		userRepository.delete(user);
	}
}
