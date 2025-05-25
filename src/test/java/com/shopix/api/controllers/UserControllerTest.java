package com.shopix.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.hamcrest.core.Is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopix.api.ApiApplication;
import com.shopix.api.auth.JWTService;
import com.shopix.api.dtos.AuthDTO;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;
import com.shopix.api.mappers.UserMapper;
import com.shopix.api.repository.UserRepository;
import com.shopix.api.testUtils.EntityFactory;
import com.shopix.api.testUtils.IntegrationUtils;
import com.shopix.api.testUtils.Utils;

import net.datafaker.Faker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Tag("integration")
public class UserControllerTest {
	@Autowired
	private UserRepository repository;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private JWTService jwt;
	@Autowired
	private IntegrationUtils utils;
	private ObjectMapper mapper;
	private Faker faker;
	
	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
		faker = new Faker();
	}

	
	@Test
	void shouldAuthenticateUser() throws Exception {
		String password = faker.internet().password();
		User user = utils.storeUser(EntityFactory.user(), password);
		AuthDTO auth = new AuthDTO(user.getUsername(), password);
		MvcResult result = mvc.perform(
				post("/api/users/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(auth)))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.token").exists())
			.andExpect(jsonPath("$.password").doesNotExist())
			.andReturn();
		String res = result.getResponse().getContentAsString();
		Map<String, String> resMap = mapper.readValue(res, Map.class);
		assertThat(jwt.isValid(resMap.get("token"), user));
	}

	@Test
	void shouldRegisterNewUser() throws Exception {
		UserCreatedDTO dto = new UserCreatedDTO(
			faker.name().firstName(),
			faker.name().fullName(),
			faker.internet().safeEmailAddress(),
			Role.CUSTOMER,
			faker.internet().password()
		);
		MvcResult result = mvc.perform(
				post("/api/users/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.username", Is.is(dto.username())))
			.andExpect(jsonPath("$.email", Is.is(dto.email())))
			.andExpect(jsonPath("$.fullname", Is.is(dto.fullname())))
			.andExpect(jsonPath("$.created_at").hasJsonPath())
			.andExpect(jsonPath("$.updated_at").hasJsonPath())
			.andExpect(jsonPath("$.password").doesNotExist())
			.andReturn();
		String res = result.getResponse().getContentAsString();
		UserResponseDTO resDto = mapper.readValue(res, UserResponseDTO.class);
		assertThat(resDto.id()).isGreaterThan(0);
		assertThat(repository.existsById(resDto.id()));
		User user = repository.findById(resDto.id()).orElseThrow();
		assertThat(user.getUsername()).isEqualTo(dto.username());
		assertThat(user.getFullname()).isEqualTo(dto.fullname());
		assertThat(user.getEmail()).isEqualTo(dto.email());
		assertThat(user.getRole()).isEqualTo(dto.role());
	}
	
	@Test
	void shouldReturnLoggedUser() throws Exception {
		String password = faker.internet().password();
		User user = utils.storeUser(EntityFactory.user(), password);
		String token = utils.MvcAuthenticate(user, password);
		mvc.perform(
				get("/api/users/me")
				.header("Authorization", "Bearer " + token)
			)
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.username", Is.is(user.getUsername())))
			.andExpect(jsonPath("$.email", Is.is(user.getEmail())))
			.andExpect(jsonPath("$.fullname", Is.is(user.getFullname())));
	}
	
	@Test
	void shouldReturnAllUsers() throws Exception {
		String password = faker.internet().password();
		User user1 = utils.storeUser(EntityFactory.user(), password);
		User user2 = utils.storeUser(EntityFactory.user(), password);
		String token = utils.MvcAuthenticate(user1, password);
		MvcResult result = mvc.perform(
				get("/api/users")
				.header("Authorization", "Bearer " + token)
			)
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("length($)", Is.is(2)))
			.andReturn();
		String res = result.getResponse().getContentAsString();
		List<UserResponseDTO> resList = Utils.readValues(res, UserResponseDTO.class);
		Optional<UserResponseDTO> user1Res = resList.stream().filter((dto) -> dto.id() == user1.getId()).findFirst();
		assertThat(user1Res).hasValue(UserMapper.toDTO(user1));
		Optional<UserResponseDTO> user2Res = resList.stream().filter((dto) -> dto.id() == user2.getId()).findFirst();
		assertThat(user2Res).hasValue(UserMapper.toDTO(user2));
		
	}
	
}
