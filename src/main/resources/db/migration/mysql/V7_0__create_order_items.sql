
create table order_items (
	id bigint auto_increment primary key,
	quantity integer not null,
	subtotal float not null,
	order_id bigint,
	product_variation_id bigint,
	foreign key (order_id) references orders(id),
	foreign key (product_variation_id) references product_variations(id)
)
