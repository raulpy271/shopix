
ALTER TABLE orders 
	ADD address_id bigint;

ALTER TABLE orders
	ADD CONSTRAINT fk_orders_address_id 
	FOREIGN KEY (address_id) REFERENCES addresses (id);