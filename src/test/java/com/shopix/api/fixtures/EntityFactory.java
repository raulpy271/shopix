package com.shopix.api.fixtures;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.shopix.api.entities.Address;
import com.shopix.api.entities.Cart;
import com.shopix.api.entities.CartItem;
import com.shopix.api.entities.Product;
import com.shopix.api.entities.ProductVariation;
import com.shopix.api.entities.Promotion;
import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;

import net.datafaker.Faker;

public class EntityFactory {
	
	private static Faker faker = new Faker();

	public static User user() {
		User u = new User();
		u.setEmail(faker.internet().safeEmailAddress());
		u.setUsername(faker.name().firstName());
		u.setFullname(faker.name().fullName());
		u.setRole(Role.CUSTOMER);
		return u;
	}
	
	public static Product product() {
		List<ProductVariation> vars = new ArrayList<>();
		Product p = new Product();
		p.setName(faker.name().title());
		p.setBrand(faker.brand().car());
		p.setCategory(faker.brand().car());
		p.setPrice(faker.number().randomDouble(2, 1, 1000));
		p.setStock(faker.number().numberBetween(100, 500));
		p.setRating((float)faker.number().randomDouble(2, 0, 5));
		for (int i = 0; i < faker.number().numberBetween(1, 4); i++) {
			vars.add(var());
		}
		p.setVars(vars);
		return p;
	}
	
	public static ProductVariation var() {
		HashMap<String, String> op = new HashMap<>();
		for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
			op.put(faker.name().firstName(), faker.word().adjective());
		}
		ProductVariation pv = new ProductVariation();
		pv.setStock(faker.number().numberBetween(1, 200));
		pv.setOptions(op);
		return pv;
	}
	
	public static Cart cart() {
		Cart c = new Cart();
		List<CartItem> items = new ArrayList<>();
		for (int i = 0; i < faker.number().numberBetween(1, 5); i++) {
			items.add(cartItem());
		}
		c.setItems(items);
		return c;
	}
	
	public static CartItem cartItem() {
		CartItem ci = new CartItem();
		ci.setQuantity(faker.number().randomDigit());
		ci.setSubtotal((float)faker.number().randomDouble(2, 100, 500));
		ci.setVar(var());
		return ci;
	}
	
	public static Promotion promotion() {
		// Cria uma promoção ativa por padrão
		Promotion p = new Promotion();
		p.setName(faker.cannabis().brands());
		p.setDiscountPercentage((float) faker.number().numberBetween(0f, 20f) / 100f);
		p.setStartDate(Date.valueOf(LocalDate.now().minusWeeks(3)));
		p.setEndDate(Date.valueOf(LocalDate.now()));
		p.setActive(true);
		return p;
	}
	
	public static Promotion promotion(boolean inactive) {
		Promotion p = promotion();
		if (inactive) {
			if (faker.bool().bool()) {
				// uma promoção inativa devido a sua desativação
				p.setActive(false);
			} else {
				// uma promoção inativa devido a sua data
				p.setEndDate(Date.valueOf(LocalDate.now().minusWeeks(1)));
			}
		}
		return p;
	}
	
	public static Address address() {
		Address a = new Address();
		a.setCity(faker.address().city());
		a.setStreet(faker.address().streetAddress());
		a.setNumber(faker.address().buildingNumber());
		a.setNeighborhood(faker.address().streetPrefix());
		a.setState(faker.address().state());
		a.setComplement(faker.address().secondaryAddress());
		a.setZipCode(faker.number().numberBetween(1000l, 10000l));
		return a;
	}
}
