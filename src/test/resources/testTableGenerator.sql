DROP TABLE IF EXISTS test_products_in_shops;

DROP TABLE IF EXISTS test_shops;

DROP TABLE IF EXISTS test_city;

DROP TABLE IF EXISTS test_street;

DROP TABLE IF EXISTS test_number;

DROP TABLE IF EXISTS test_products;

DROP TABLE IF EXISTS test_category;

CREATE TABLE IF NOT EXISTS test_city
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS test_street
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS test_number
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS test_shops
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL UNIQUE,
    test_city_id INT REFERENCES test_city (test_id) ON DELETE CASCADE,
    test_ INT REFERENCES test_street (test_id) ON DELETE CASCADE,
    test_number_id INT REFERENCES test_number (test_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS test_category
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS test_products
(
    test_id SERIAL PRIMARY KEY,
    test_name VARCHAR(128) NOT NULL,
    test_category_id INT REFERENCES test_category (test_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS test_products_in_shops
(
    test_id SERIAL PRIMARY KEY,
    test_shop_id INT,
    test_product_id INT,
    test_quantity INT NOT NULL
);