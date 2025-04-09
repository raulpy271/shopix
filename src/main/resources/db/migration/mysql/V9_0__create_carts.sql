
create table carts (
	id bigint auto_increment primary key,
	user_id bigint,
	foreign key (user_id) references users(id)
)
