
ALTER TABLE cart_items
	ADD promotion_id bigint;

ALTER TABLE cart_items
	ADD CONSTRAINT fk_cart_items_promotion_id 
	FOREIGN KEY (promotion_id) REFERENCES promotions (id);