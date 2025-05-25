package com.shopix.api.testUtils;

import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;

import net.datafaker.Faker;

public class EntityFactory {
	
	private static Faker faker = new Faker();

	public static User user() {
		User u = new User();
		u.setId(faker.number().randomNumber(3));
		u.setEmail(faker.internet().safeEmailAddress());
		u.setUsername(faker.name().firstName());
		u.setFullname(faker.name().fullName());
		u.setRole(Role.CUSTOMER);
		return u;
	}
}
