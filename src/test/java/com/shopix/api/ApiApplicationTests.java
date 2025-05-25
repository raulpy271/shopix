package com.shopix.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.shopix.api.controllers.UserController;
import com.shopix.api.repository.UserRepository;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("integration")
class ApiApplicationTests {

	@Autowired
	private UserController controller;
	@Autowired
	private UserRepository repository;
	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
		assertThat(mvc).isNotNull();
		assertThat(repository).isNotNull();
		assertThat(repository.findAll()).isEmpty();
	}
	

}
