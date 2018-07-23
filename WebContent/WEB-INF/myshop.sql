SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS dingdan;
DROP TABLE IF EXISTS shopcar;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS wine;



/* Create Tables */

CREATE TABLE dingdan
(
	dd_id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	wine_id int NOT NULL,
	-- 购买数量
	number int DEFAULT 1 NOT NULL COMMENT '购买数量',
	PRIMARY KEY (dd_id)
);


CREATE TABLE shopcar
(
	car_id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	wine_id int NOT NULL,
	-- 购物车商品数量
	number int DEFAULT 1 NOT NULL COMMENT '购物车商品数量',
	PRIMARY KEY (car_id)
);



CREATE TABLE user
(
	user_id int NOT NULL AUTO_INCREMENT,
	user_name varchar(20) NOT NULL,
	password varchar(99) NOT NULL,
	address varchar(20),
	phone int(11),
	picture varchar(99),
	PRIMARY KEY (user_id),
	UNIQUE (user_name)
);


CREATE TABLE wine
(
	wine_id int NOT NULL,
	wine_name varchar(20) NOT NULL,
	kind varchar(20) NOT NULL,
	price float NOT NULL,
	detail text,
	picture varchar(50) NOT NULL,
	PRIMARY KEY (wine_id)
);



/* Create Foreign Keys */

ALTER TABLE dingdan
	ADD FOREIGN KEY (user_id)
	REFERENCES user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE shopcar
	ADD FOREIGN KEY (user_id)
	REFERENCES user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dingdan
	ADD FOREIGN KEY (wine_id)
	REFERENCES wine (wine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE shopcar
	ADD FOREIGN KEY (wine_id)
	REFERENCES wine (wine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



