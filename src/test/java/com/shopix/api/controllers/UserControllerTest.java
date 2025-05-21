package com.shopix.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopix.api.ApiApplication;
import com.shopix.api.dtos.UserCreatedDTO;
import com.shopix.api.dtos.UserResponseDTO;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;
import com.shopix.api.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private UserController controller;
	@Autowired
	private UserRepository repository;
	@Autowired
	private MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();


	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		assertThat(mvc).isNotNull();
	}

	@Test
	void shouldRegisterNewUser() throws Exception {
		UserCreatedDTO dto = new UserCreatedDTO(
			"raul12347",
			"Raul Martins",
			"raul7@gmail.com",
			Role.CUSTOMER,
			"password1234"
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
			.andExpect(jsonPath("$.created_at").exists())
			.andExpect(jsonPath("$.updated_at").exists())
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
}
