package com.shopix.api.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.HamcrestCondition.matching;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hamcrest.object.HasEqualValues;

import com.shopix.api.dtos.ProductCreateDTO;
import com.shopix.api.dtos.ProductResponseDTO;
import com.shopix.api.entities.Product;
import com.shopix.api.fixtures.EntityFactory;

import net.datafaker.Faker;

public class ProductMapperTest {
	
	private Faker faker;
	private Product product;

	@BeforeEach
	public void setUp() {
		faker = new Faker();
		product = EntityFactory.product();
		product.setId(faker.number().numberBetween(1L, 100L));
		for (int i = 0; i < product.getVars().size(); i++) {
			product.getVars().get(i).setId(faker.number().numberBetween(1L, 100L));
		}
	}
	
	@Test
	void shouldCreateProductResponseDTO() {
		ProductResponseDTO dto = ProductMapper.toDTO(product);
		assertThat(dto.id()).isEqualTo(product.getId());
		assertThat(dto.name()).isEqualTo(product.getName());
		assertThat(dto.category()).isEqualTo(product.getCategory());
		assertThat(dto.brand()).isEqualTo(product.getBrand());
		assertThat(dto.price()).isEqualTo(product.getPrice());
		assertThat(dto.vars().size()).isEqualTo(product.getVars().size());
		for (int i = 0; i < dto.vars().size(); i++) {
			assertThat(dto.vars().get(i).id()).isEqualTo(product.getVars().get(i).getId());
			assertThat(dto.vars().get(i).options()).isEqualTo(product.getVars().get(i).getOptions());
		}
	}
	
	@Test
	void shouldCreateProduct() {
		ProductCreateDTO dto = new ProductCreateDTO(
			this.product.getName(),
			this.product.getPrice(),
			this.product.getStock(),
			this.product.getCategory(),
			this.product.getBrand(),
			this.product.getRating(),
			List.of()
		);
		Product product = ProductMapper.toEntity(dto);
		assertThat(product).is(matching(new HasEqualValues(this.product)));
	}
}
