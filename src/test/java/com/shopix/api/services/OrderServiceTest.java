package com.shopix.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.HamcrestCondition.matching;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.shopix.api.ApiApplication;
import com.shopix.api.dtos.BuyItemDTO;
import com.shopix.api.dtos.OrderBuyDTO;
import com.shopix.api.dtos.OrderResponseDTO;
import com.shopix.api.entities.Address;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.entities.Order;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.ProductVariation;
import com.shopix.api.entities.Promotion;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.OrderStatus;
import com.shopix.api.fixtures.EntityFactory;
import com.shopix.api.repository.AddressRepository;
import com.shopix.api.repository.CartRepository;
import com.shopix.api.repository.OrderRepository;
import com.shopix.api.repository.ProductRepository;
import com.shopix.api.repository.PromotionRepository;
import com.shopix.api.repository.UserRepository;

import net.datafaker.Faker;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = ApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Tag("integration")
public class OrderServiceTest {
	@Autowired
	private OrderRepository repository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private PromotionRepository promotionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private OrderService service;

	private Faker faker;
	private User user;
	private Address address;
	private Product product;
	private Cart cart;
	private Promotion promotion;

	private Authentication auth = mock(Authentication.class);
	
	@BeforeEach
	void setUp() {
		faker = new Faker();
		product = productRepository.save(EntityFactory.product());
		user = userRepository.save(EntityFactory.user());
		when(auth.getPrincipal()).thenReturn(user);
		address = addressRepository.save(EntityFactory.address());
		for (ProductVariation var : product.getVars()) {
			var.setProduct(product);
		}
		promotion = EntityFactory.promotion();
		cart = EntityFactory.cart();
		for (CartItem item: cart.getItems()) {
			item.setCart(cart);
			item.setVar(
				product.getVars().get(faker.number().numberBetween(0, product.getVars().size()))
			);
			item.setQuantity(faker.number().numberBetween(1, item.getVar().getStock()));
		}
		cart = cartRepository.save(cart);
	}
	
	@RepeatedTest(3)
	void shouldBuySingleProduct() throws Exception {
		CartItem item = cart.getItems().get(faker.number().numberBetween(0, cart.getItems().size()));
		int varStock = item.getVar().getStock();
		int quantity = item.getQuantity();
		BuyItemDTO itemDto = new BuyItemDTO(Optional.empty(), item.getId());
		OrderBuyDTO dto = new OrderBuyDTO("PIX", address.getId(), List.of(itemDto));
		double expectedPrice = item.getVar().getProduct().getPrice() * quantity;
		OrderResponseDTO orderRes = service.buy(auth, dto);
		assertThat(repository.existsById(orderRes.id())).isTrue();
		Order order = repository.findById(orderRes.id()).get();
		assertThat(order.getItems().size()).isEqualTo(dto.items().size());
		assertThat((double)order.getTotalPrice()).is(matching(closeTo(expectedPrice, 0.004)));
		assertThat((double)order.getItems().get(0).getSubtotal()).is(matching(closeTo(expectedPrice, 0.004)));
		assertThat(order.getItems().get(0).getVar().getStock()).isEqualTo(varStock - quantity);
		assertThat(order.getItems().get(0).getQuantity()).isEqualTo(item.getQuantity());
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING_PAYMENT);
	}

	@Test
	void shouldBuyProductWithPromotion() throws Exception {
		this.promotion.setProduct(cart.getItems().get(0).getVar().getProduct());
		Promotion p = promotionRepository.save(this.promotion);
		BuyItemDTO item = new BuyItemDTO(Optional.of(p.getId()), cart.getItems().get(0).getId());
		OrderBuyDTO dto = new OrderBuyDTO("PIX", address.getId(), List.of(item));
		double expectedPrice = (
			cart.getItems().get(0).getVar().getProduct().getPrice() *
			cart.getItems().get(0).getQuantity() *
			(1f - p.getDiscountPercentage())
		);
		OrderResponseDTO orderRes = service.buy(auth, dto);
		assertThat(repository.existsById(orderRes.id())).isTrue();
		Order order = repository.findById(orderRes.id()).get();
		assertThat(order.getItems().size()).isEqualTo(dto.items().size());
		assertThat((double)order.getTotalPrice()).is(matching(closeTo(expectedPrice, 0.004)));
		assertThat((double)order.getItems().get(0).getSubtotal()).is(matching(closeTo(expectedPrice, 0.004)));
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING_PAYMENT);
	}
	
	@Test
	void shouldValidateInactivePromotion() {
		Promotion inactive = EntityFactory.promotion(true);
		inactive.setProduct(cart.getItems().get(0).getVar().getProduct());
		inactive = promotionRepository.save(inactive);
		BuyItemDTO item = new BuyItemDTO(Optional.of(inactive.getId()), cart.getItems().get(0).getId());
		OrderBuyDTO dto = new OrderBuyDTO("PIX", address.getId(), List.of(item));
		assertThatThrownBy(() -> service.buy(auth, dto)).hasMessageContaining("está inativa");
	}
	
	@Test
	void shouldValidateStock() {
		CartItem item = cart.getItems().get(faker.number().numberBetween(0, cart.getItems().size()));
		item.setQuantity(item.getVar().getStock() + 1);
		cartRepository.save(cart);
		BuyItemDTO itemDto = new BuyItemDTO(Optional.empty(), item.getId());
		OrderBuyDTO dto = new OrderBuyDTO("PIX", address.getId(), List.of(itemDto));
		assertThatThrownBy(() -> service.buy(auth, dto)).hasMessageContaining("Não há stoque");
	}
}
