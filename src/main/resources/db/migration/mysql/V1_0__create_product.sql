
create table products (
	id bigint auto_increment primary key,
	name varchar(50) not null,
	price float not null,
	stock int not null,
	category varchar(255),
	brand varchar(50),
	rating float,
	created_at timestamp,
	updated_at timestamp 
);
