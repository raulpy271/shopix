
create table payments (
	id bigint auto_increment primary key,
	amount float not null,
	method varchar(50) check (method in ('CREDIT_CARD','DEBIT_CARD','PIX')),
	status varchar(50) check (status in ('APROVED','PENDING','PROCESSING','REJECTED')),
	created_at timestamp,
	updated_at timestamp,
	order_id bigint,
	foreign key (order_id) references orders(id)
)
