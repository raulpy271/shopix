package com.shopix.api.entities;

import java.sql.Date;

import com.shopix.api.enuns.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Setter
@Getter
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String username;
	private String fullname;
	private String email;
	private boolean admin;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String password_hash;
	private String password_salt;
	private Date created_at;
	private Date updated_at;
}
