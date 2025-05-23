package com.shopix.api.auth;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.Jwts;

@Configuration
public class SecretKeyBuilder {
	@Bean
	public SecretKey secretKey() {
		return Jwts.SIG.HS256.key().build();
	}
}
