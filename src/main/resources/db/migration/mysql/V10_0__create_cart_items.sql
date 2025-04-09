
create table cart_items (
	id bigint auto_increment primary key,
	quantity integer not null,
	subtotal float not null,
	cart_id bigint,
	product_variation_id bigint,
	foreign key (cart_id) references carts(id),
	foreign key (product_variation_id) references product_variations(id)
)
