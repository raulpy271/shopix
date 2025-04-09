
create table reviews (
	id bigint auto_increment primary key,
	rating int not null,
	comment varchar(255),
	user_id bigint,
	product_variation_id bigint,
	created_at timestamp,
	updated_at timestamp,
	foreign key (user_id) references users(id),
	foreign key (product_variation_id) references product_variations(id)
)