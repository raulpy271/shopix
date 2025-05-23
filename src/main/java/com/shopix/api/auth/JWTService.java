package com.shopix.api.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.shopix.api.entities.User;

import io.jsonwebtoken.Jwts;

@Service
public class JWTService {
	private final SecretKey secret;
	
	JWTService(SecretKey secret) {
		this.secret = secret;
	}
	
	public String generateToken(User user) {
		return Jwts
			.builder()
			.subject(user.getUsername())
			.claim("name", user.getUsername())
			.claim("id", user.getId())
			.claim("role", user.getRole())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + 86400000))
			.signWith(secret)
			.compact();
	}
	
	public String extractUsername(String token)
	{
		return Jwts
				.parser()
				.verifyWith(secret)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean isValid(String token, UserDetails user)
	{
		return extractUsername(token).equals(user.getUsername());
	}
}
