
create table addresses (
	id bigint auto_increment primary key,
	street varchar(255),
	number varchar(10),
	neighborhood varchar(255),
	city varchar(255),
	state varchar(255),
	complement varchar(255),
	zip_code bigint,
	user_id bigint,
	foreign key (user_id) references users(id)
);