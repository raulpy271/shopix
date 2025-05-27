package com.shopix.api.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.shopix.api.entities.Promotion;
import com.shopix.api.fixtures.EntityFactory;

public class PromotionServiceTest {
	
	private Promotion valid;
	
	@BeforeEach
	void setUp() {
		valid = EntityFactory.promotion();
	}
	
	@Test
	void testValidPromotion() {
		assertThat(PromotionService.validPromotion(valid)).isTrue();
	}

	@ParameterizedTest
	@MethodSource("inactives")
	void testInvalidPromotion(Promotion promotion) {
		assertThat(PromotionService.validPromotion(promotion)).isFalse();
	}

	static Stream<Promotion> inactives() {
		return Stream.of(
			EntityFactory.promotion(true),
			EntityFactory.promotion(true),
			EntityFactory.promotion(true)
		);
	}
}
