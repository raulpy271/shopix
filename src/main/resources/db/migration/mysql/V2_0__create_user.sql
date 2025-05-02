
create table users (
	id bigint auto_increment primary key,
	username varchar(50) not null unique,
	fullname varchar(255) not null,
	email varchar(100) not null unique,
	admin boolean default FALSE,
	role varchar(100) check (role in ('SELLER', 'BUYER')),
	password_hash varchar(255),
	password_salt varchar(255),
	created_at timestamp,
	updated_at timestamp
);