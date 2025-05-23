package com.shopix.api.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

import com.shopix.api.entities.User;
import com.shopix.api.enuns.Role;

public class JWTServiceTest {
	SecretKey secret = Jwts.SIG.HS256.key().build();
	JWTService service = new JWTService(secret);
	
	@Test
	void shouldGenerateToken() throws Exception {
		User user = new User();
		user.setId(1l);
		user.setUsername("raul");
		user.setEmail("raul@gmail.com");
		user.setFullname("Raul");
		user.setRole(Role.CUSTOMER);
		String token = service.generateToken(user);
		assertThat(token).isNotNull();
		assertThat(token.length()).isGreaterThan(0);
		Jws<Claims> claims = Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
		assertThat(claims.getPayload().get("id", Long.class)).isEqualTo(user.getId());
		assertThat(claims.getPayload().getSubject()).isEqualTo(user.getUsername());
	}
	
	@Test
	void shouldExtractUsername() throws Exception {
		User user = new User();
		user.setId(1l);
		user.setUsername("raul");
		user.setEmail("raul@gmail.com");
		user.setFullname("Raul");
		user.setRole(Role.CUSTOMER);
		String token = service.generateToken(user);
		assertThat(service.extractUsername(token)).isEqualTo(user.getUsername());
	}
	
	@Test
	void shouldAcceptValidToken() throws Exception {
		User user = new User();
		user.setId(1l);
		user.setUsername("raul");
		user.setEmail("raul@gmail.com");
		user.setFullname("Raul");
		user.setRole(Role.CUSTOMER);
		String token = service.generateToken(user);
		assertThat(service.isValid(token, user)).isTrue();
	}
	
	@Test
	void shouldRejectInvalidToken() throws Exception {
		User user = new User();
		String token = "asdfafdfdsaasdfad";
		assertThatThrownBy(() -> {service.isValid(token, user);}).isInstanceOf(MalformedJwtException.class);
	}

	@Test
	void shouldRejectTokenWithDifferentSign() throws Exception {
		User user = new User();
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
		assertThatThrownBy(() -> {service.isValid(token, user);}).isInstanceOf(SignatureException.class);
	}

}
