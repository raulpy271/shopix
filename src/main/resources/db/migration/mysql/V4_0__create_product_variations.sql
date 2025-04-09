
create table product_variations (
	id bigint auto_increment primary key,
	options varbinary(255),
	stock integer not null,
	product_id bigint,
	foreign key (product_id) references products(id)
)
