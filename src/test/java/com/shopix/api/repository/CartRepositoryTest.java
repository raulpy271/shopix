package com.shopix.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.HamcrestCondition.matching;

import java.util.List;
import java.util.Optional;

import org.hamcrest.object.HasEqualValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shopix.api.ApiApplication;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.ProductVariation;
import com.shopix.api.fixtures.EntityFactory;

import net.datafaker.Faker;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Tag("integration")
public class CartRepositoryTest {
	@Autowired
	private CartRepository repository;
	@Autowired
	private ProductRepository productRepository;
	private Cart cart;
	private Faker faker;
	
	@BeforeEach
	void setUp() {
		faker = new Faker();
		Product product = EntityFactory.product();
		ProductVariation var = EntityFactory.var();
		product.setVars(List.of(var));
		product = productRepository.save(product);
		cart = EntityFactory.cart();
		for (CartItem item: cart.getItems()) {
			item.setVar(product.getVars().get(0));
		}
		cart = repository.save(cart);
	}
	
	@Test
	void shouldListCartItems() {
		List<CartItem> items = repository.listCartItems(cart.getId());
		assertThat(items.size()).isEqualTo(cart.getItems().size());
		for (int i = 0; i < items.size(); i++) {
			final CartItem expectedItem = cart.getItems().get(i);
			Optional<CartItem> resultItem = items
				.stream()
				.filter((item) -> item.getId() == expectedItem.getId())
				.findFirst();
			assertThat(resultItem).isNotEmpty();
			assertThat(resultItem.get()).is(matching(new HasEqualValues(expectedItem)));
		}
	}
	
	@Test
	void shouldReturnEmptyItem () {
		CartItem item = repository.getCartItemById(faker.number().numberBetween(10000L, 500000L));
		assertThat(item).isNull();
	}
	
	@TestFactory
	List<DynamicTest> shouldGetCartItem() {
		return cart
			.getItems()
			.stream()
			.map((item) -> dynamicTest(
				"get Cart Item " + item.getId(), 
				() -> assertThat(repository.getCartItemById(item.getId())).is(matching(new HasEqualValues(item)))
			))
			.toList();
	}
}
