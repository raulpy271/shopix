package com.shopix.api.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shopix.api.ApiApplication;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.dtos.UserUpdateDTO;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;
import com.shopix.api.fixtures.EntityFactory;
import com.shopix.api.fixtures.IntegrationUtils;
import com.shopix.api.repository.UserRepository;

import net.datafaker.Faker;


@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Tag("integration")
public class UserServiceTest {
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserService service;
	@Autowired
	private IntegrationUtils utils;
	private User user;
	private String password;
	private Faker faker;
	
	@BeforeEach
	public void setUp() {
		faker = new Faker();
		user = EntityFactory.user();
		password = faker.internet().password();
	}

	@Test
	public void shouldStoreUser() {
		UserCreatedDTO dto = new UserCreatedDTO(
			user.getUsername(),
			user.getFullname(),
			user.getEmail(),
			user.getRole(),
			password
		);
		UserResponseDTO res = service.store(dto);
		assertThat(repository.existsById(res.id())).isTrue();
		User userRep = repository.findById(res.id()).get();
		assertThat(userRep.getFullname()).isEqualTo(dto.fullname());
		assertThat(userRep.getUsername()).isEqualTo(dto.username());
		assertThat(userRep.getEmail()).isEqualTo(dto.email());
		assertThat(userRep.getPassword_hash()).isNotEqualTo(dto.password());
		assertThat(userRep.getPassword_salt()).isNotEmpty();
	}
	
	@Test
	public void shouldUpdateFullnameUser() {
		String newFullname = faker.name().fullName();
		User user = utils.storeUser(this.user, password);
		UserUpdateDTO dto = new UserUpdateDTO(
			user.getId(),
			user.getUsername(),
			newFullname,
			user.getEmail(),
			user.getRole()
		);
		service.update(dto);
		assertThat(repository.existsById(user.getId())).isTrue();
		User userUpdated = repository.findById(user.getId()).get();
		assertThat(user.getUsername()).isEqualTo(userUpdated.getUsername());
		assertThat(newFullname).isEqualTo(userUpdated.getFullname());
		assertThat(user.getEmail()).isEqualTo(userUpdated.getEmail());
		assertThat(user.getRole()).isEqualTo(userUpdated.getRole());
	}
	
	@Test
	public void shouldDestroyUser() {
		User user = utils.storeUser(this.user, password);
		service.destroy(user.getId());
		assertThat(repository.findById(user.getId())).isEmpty();
	}
}
