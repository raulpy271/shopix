package com.shopix.api.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shopix.api.dtos.CartCreateDTO;
import com.shopix.api.dtos.CartResponseDTO;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.User;
import com.shopix.api.fixtures.EntityFactory;
import com.shopix.api.repository.UserRepository;

import net.datafaker.Faker;

public class CartMapperTest {

	private Faker faker;
	private User user;
	private Cart cart;
	private UserRepository repository;

	@BeforeEach
	public void setUp() {
		faker = new Faker();
		cart = EntityFactory.cart();
		user = EntityFactory.user();
		repository = mock(UserRepository.class);
		user.setId(faker.number().numberBetween(1L, 100L));
		cart.setId(faker.number().numberBetween(1L, 100L));
		cart.setUser(user);
		for (int i = 0; i < cart.getItems().size(); i++) {
			cart.getItems().get(i).setId(faker.number().numberBetween(1L, 100L));
		}
	}

	@Test
	void shouldCreateCartResponseDTO() {
		CartResponseDTO dto = CartMapper.toDTO(cart);
		assertThat(dto.user_id()).isEqualTo(user.getId());
		assertThat(dto.items().size()).isEqualTo(cart.getItems().size());
		for (int i = 0; i < cart.getItems().size(); i++) {
			assertThat(dto.items().get(i).quantity()).isEqualTo(cart.getItems().get(i).getQuantity());
			assertThat(dto.items().get(i).subtotal()).isEqualTo(cart.getItems().get(i).getSubtotal());
		}
	}
	
	@Test
	void shouldCreateCart() {
		when(repository.findById(user.getId())).thenReturn(Optional.of(user));
		CartCreateDTO dto = new CartCreateDTO(user.getId());
		Cart cart = CartMapper.toEntity(dto, repository);
		assertThat(cart.getUser().getId()).isEqualTo(user.getId());
	}
}
