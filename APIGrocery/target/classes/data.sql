CREATE TABLE IF NOT EXISTS groceryitem (
	groceryitemid SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	price DOUBLE PRECISION,inventory INT);


CREATE TABLE IF NOT EXISTS order_tbl (
	orderid SERIAL PRIMARY KEY,
	userid BIGINT, 
	orderdate TIMESTAMP,
	status VARCHAR(255));



CREATE TABLE IF NOT EXISTS orderitem (
 	orderitemid SERIAL PRIMARY KEY, 
 	orderid BIGINT REFERENCES order_tbl(orderid),
 	groceryitemid BIGINT REFERENCES groceryitem(groceryitemid),
 	quantity INT);
 	
 	
 --Insert statement