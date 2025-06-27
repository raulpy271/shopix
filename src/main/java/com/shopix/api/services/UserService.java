package com.shopix.api.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import com.shopix.api.dtos.AddressCreateDTO;
import com.shopix.api.dtos.AddressResponseDTO;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.dtos.UserUpdateDTO;
import com.shopix.api.entities.Address;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.User;
import com.shopix.api.mappers.AddressMapper;
import com.shopix.api.mappers.UserMapper;
import com.shopix.api.repository.AddressRepository;
import com.shopix.api.repository.CartRepository;
import com.shopix.api.repository.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartRepository cartRepository;
	private SecureRandom random;
	
	public UserService()
	{
		this.random = new SecureRandom();
	}
	
	public UserResponseDTO me(Authentication auth)
	{
		User user = (User) auth.getPrincipal();
		return UserMapper.toDTO(user);
	}

	public UserResponseDTO store(UserCreatedDTO created)
	{
		User user = UserMapper.toEntity(created);
		String salt = "";
		for (int i = 0; i < 32; i++) {
			salt += String.valueOf(random.nextInt(8));
		}
		user.setPassword_hash(new BCryptPasswordEncoder().encode(created.password() + salt));
		user.setPassword_salt(salt);
		user = userRepository.save(user);
		Cart cart = new Cart();
		cart.setUser(user);
		cartRepository.save(cart);
		return UserMapper.toDTO(user);
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
	
	public List<AddressResponseDTO> addresses(Authentication auth)
	{
		User user = (User) auth.getPrincipal();
		List<Address> addrs = addressRepository.getAddressByUserId(user.getId());
		return addrs.stream().map(AddressMapper::toDTO).toList();
	}

	public AddressResponseDTO createAddresses(AddressCreateDTO dto, Authentication auth)
	{
		User user = (User) auth.getPrincipal();
		Address address = AddressMapper.toEntity(dto);
		address.setUser(user);
		return AddressMapper.toDTO(addressRepository.save(address));
	}
}
