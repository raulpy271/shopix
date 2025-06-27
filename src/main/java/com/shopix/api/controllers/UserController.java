package com.shopix.api.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.auth.JWTService;
import com.shopix.api.dtos.AddressCreateDTO;
import com.shopix.api.dtos.AddressResponseDTO;
import com.shopix.api.dtos.AuthDTO;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.dtos.UserUpdateDTO;
import com.shopix.api.entities.User;
import com.shopix.api.repository.UserRepository;
import com.shopix.api.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final UserRepository userRepository;
	
	
	public UserController(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@PostMapping("/auth")
	public ResponseEntity<?> auth(@RequestBody AuthDTO dto)
	{
		Optional<User> userOpt = userRepository.findByUsername(dto.username());
		if (userOpt.isEmpty()) {
			return new ResponseEntity<>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		String password = dto.password() + userOpt.get().getPassword_salt();
		Authentication auth = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(dto.username(), password)
		);
		User user = (User) auth.getPrincipal();
		String token = jwtService.generateToken(user);
		return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> store(@RequestBody UserCreatedDTO user)
	{
		UserResponseDTO created = userService.store(user);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserResponseDTO> me(Authentication auth)
	{
		return new ResponseEntity<>(userService.me(auth), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> list()
	{
		return new ResponseEntity<>(userService.list(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDTO> show(@PathVariable long id)
	{
		try {
			return new ResponseEntity<>(userService.show(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PatchMapping
	public ResponseEntity<UserResponseDTO> update(@RequestBody UserUpdateDTO update)
	{
		try {
			return new ResponseEntity<>(userService.update(update), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> destroy(@PathVariable long id)
	{
		try {
			userService.destroy(id);
			return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressResponseDTO>> addresses(Authentication auth)
	{
		return new ResponseEntity<>(userService.addresses(auth), HttpStatus.OK);
	}
	
	@PostMapping("/addresses")
	public ResponseEntity<AddressResponseDTO> createAddresses(@RequestBody AddressCreateDTO dto, Authentication auth)
	{
		return new ResponseEntity<>(userService.createAddresses(dto, auth), HttpStatus.OK);
	}
}
