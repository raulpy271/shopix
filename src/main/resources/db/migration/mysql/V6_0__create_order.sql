
create table orders (
	id bigint auto_increment primary key,
	total_price float not null,
	status varchar(50) check (status in ('PENDING_PAYMENT', 'PAYED', 'SENT', 'DELIVERED')),
	payment_method varchar(100) not null,
	tracking_code varchar(255),
	created_at timestamp,
	updated_at timestamp,
	user_id bigint,
	foreign key (user_id) references users(id)
)