package com.shopix.api.testUtils;

import com.shopix.api.entities.Product;
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
		Product p = new Product();
		p.setName(faker.name().title());
		p.setBrand(faker.brand().car());
		p.setCategory(faker.brand().car());
		p.setPrice(faker.number().randomDouble(2, 1, 1000));
		p.setStock(faker.number().numberBetween(100, 500));
		p.setRating((float)faker.number().randomDouble(2, 0, 5));
		return p;
	}
}
