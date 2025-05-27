package com.shopix.api.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shopix.api.ApiApplication;
import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.dtos.ProductUpdateDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.fixtures.EntityFactory;
import com.shopix.api.fixtures.IntegrationUtils;
import com.shopix.api.repository.ProductRepository;

import net.datafaker.Faker;


@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Tag("integration")
public class ProductServiceTest {
	@Autowired
	private ProductRepository repository;
	@Autowired
	private ProductService service;
	private Product product;
	private Faker faker;
	
	@BeforeEach
	public void setUp() {
		faker = new Faker();
		product = EntityFactory.product();
	}

	@Test
	public void shouldStoreProduct() {
		ProductCreateDTO dto = new ProductCreateDTO(
			product.getName(),
			product.getPrice(),
			product.getStock(),
			product.getCategory(),
			product.getBrand(),
			product.getRating(),
			List.of()
		);
		ProductResponseDTO res = service.store(dto);
		assertThat(repository.existsById(res.id())).isTrue();
		Product productRep = repository.findById(res.id()).get();
		assertThat(productRep.getName()).isEqualTo(dto.name());
		assertThat(productRep.getPrice()).isEqualTo(dto.price());
		assertThat(productRep.getStock()).isEqualTo(dto.stock());
		assertThat(productRep.getCategory()).isEqualTo(dto.category());
		assertThat(productRep.getBrand()).isEqualTo(dto.brand());
		assertThat(productRep.getRating()).isEqualTo(dto.rating());
	}
	
	@Test
	public void shouldUpdateNameProduct() {
		String newName = faker.name().title();
		Product product = repository.save(this.product);
		ProductUpdateDTO dto = new ProductUpdateDTO(
			product.getId(),
			newName,
			product.getPrice(),
			product.getStock(),
			product.getCategory(),
			product.getBrand(),
			product.getRating()
		);
		service.update(dto);
		assertThat(repository.existsById(product.getId())).isTrue();
		Product productUpdated = repository.findById(product.getId()).get();
		assertThat(product.getPrice()).isEqualTo(productUpdated.getPrice());
		assertThat(newName).isEqualTo(productUpdated.getName());
		assertThat(product.getStock()).isEqualTo(productUpdated.getStock());
		assertThat(product.getCategory()).isEqualTo(productUpdated.getCategory());
		assertThat(product.getBrand()).isEqualTo(productUpdated.getBrand());
	}
	
	@Test
	public void shouldDestroyProduct() {
		Product product = repository.save(this.product);
		service.destroy(product.getId());
		assertThat(repository.findById(product.getId())).isEmpty();
		assertThat(repository.findAll()).isEmpty();
	}
}