
/**
 * CUSTOMER TEST
 */
INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (1,'UPDATE_USER ',3000.0,1000.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');
INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (2,'DELETE CUSTOMER ',3000.0,1000.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');
INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (3,'LIMIT VALIDATION',3000.0,1000.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');


/**
 * PRODUCT TEST
 */
INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(1, 'product Test', 10.0, 10, '2015-10-22 11:38:03', '2015-10-22 11:38:03');
INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(2, 'product delete', 10.0, 10, '2015-10-22 11:38:03', '2015-10-22 11:38:03');

/**
 * SALE ORDER TEST
 */
INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (10,'UPDATE_USER ',3000.0,1000.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');

INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(10, 'product 1', 10.0, 10, '2015-10-22 11:38:03', '2015-10-22 11:38:03');
INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(11, 'product 2', 20.0, 10, '2015-10-22 11:38:03', '2015-10-22 11:38:03');
INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(12, 'product 3', 30.0, 100, '2015-10-22 11:38:03', '2015-10-22 11:38:03');



/**
 * UPDATE AND DELETE TEST  
 */
INSERT INTO product (id, description, price, quantity, create_date, update_date) VALUES(13, 'product 4', 40.0, 0, '2015-10-22 11:38:03', '2015-10-22 11:38:03');
INSERT INTO sale_order(id,customer_id, total_price, create_date, update_date) VALUES (3, 10, 40.0, '2015-10-22 11:38:03', '2015-10-22 11:38:03');
INSERT INTO order_line(id, sale_order_id, product_id, quantity, product_price, create_date, update_date) VALUES (1, 3, 13, 10, 40.0, '2015-10-22 11:38:03', '2015-10-22 11:38:03');


/**
 * INTEGRATION TEST DELETE CUSTOMER
 */
INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (100,'LIMIT VALIDATION',3000.0,1000.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');


/**
 * SALE ORDER INTEGRATION TEST
 */
INSERT INTO sale_order(id,customer_id, total_price) VALUES (5, 10, 10.0);
INSERT INTO order_line(id, sale_order_id, product_id, quantity, product_price) VALUES (3, 5, 13, 10, 10.0);


INSERT INTO customer (id,name,credit_limit,current_credit_limit,create_date,update_date,address,phone1,phone2) VALUES (8,'Customer without credit',100.0,100.0,'2015-10-22 11:38:03','2015-10-22 11:38:59','HLobo','312312312','312423423');


