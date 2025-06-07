
create table promotions (
	id bigint auto_increment primary key,
	discount_percentage float not null,
	end_date date,
	is_active bit not null,
	name varchar(255),
	start_date date,
	product_id bigint,
	foreign key (product_id) references products (id)
)
