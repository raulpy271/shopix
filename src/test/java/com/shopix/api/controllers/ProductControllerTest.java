package com.shopix.api.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopix.api.ApiApplication;
import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.dtos.ProductUpdateDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.User;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.testUtils.EntityFactory;
import com.shopix.api.testUtils.IntegrationUtils;

import net.datafaker.Faker;

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
@Transactional
@Tag("integration")
public class ProductControllerTest {
	@Autowired
	private ProductRepository repository;
	@Autowired
	private IntegrationUtils utils;
	@Autowired
	private MockMvc mvc;
	private Product product;
	private Faker faker;
	private ObjectMapper mapper;
	private String password;
	private User user;
	private String token;
	
	@BeforeEach
	public void setUp() throws Exception {
		faker = new Faker();
		product = EntityFactory.product();
		mapper = new ObjectMapper();
		password = faker.internet().password();
		user = utils.storeUser(EntityFactory.user(), password);
		token = utils.MvcAuthenticate(user, password);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductCreateDTO dto = new ProductCreateDTO(
			product.getName(),
			product.getPrice(),
			product.getStock(),
			product.getCategory(),
			product.getBrand(),
			product.getRating(),
			List.of()
		);
		MvcResult result = mvc.perform(
				post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto))
				.header("Authorization", "Bearer " + token))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name", Is.is(dto.name())))
			.andExpect(jsonPath("$.price", Is.is(dto.price())))
			.andExpect(jsonPath("$.stock", Is.is(dto.stock())))
			.andExpect(jsonPath("$.category", Is.is(dto.category())))
			.andExpect(jsonPath("$.brand", Is.is(dto.brand())))
			.andReturn();
		String res = result.getResponse().getContentAsString();
		ProductResponseDTO resDto = mapper.readValue(res, ProductResponseDTO.class);
		assertThat(repository.existsById(resDto.id())).isTrue();
		assertThat(resDto.rating()).isEqualTo(dto.rating());
	}
	
	@Test
	void shouldgetAProduct() throws Exception {
		Product product = repository.save(this.product);
		MvcResult result = mvc.perform(
				get("/api/products/" + product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", Is.is(product.getId().intValue())))
			.andExpect(jsonPath("$.name", Is.is(product.getName())))
			.andExpect(jsonPath("$.price", Is.is(product.getPrice())))
			.andExpect(jsonPath("$.stock", Is.is(product.getStock())))
			.andExpect(jsonPath("$.category", Is.is(product.getCategory())))
			.andExpect(jsonPath("$.brand", Is.is(product.getBrand())))
			.andReturn();
	}

}
