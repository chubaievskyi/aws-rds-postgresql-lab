CREATE INDEX IF NOT EXISTS idx_category_id ON products (category_id);
CREATE INDEX IF NOT EXISTS idx_product_id ON products_in_shops (product_id);

CREATE INDEX IF NOT EXISTS idx_category_name ON products (name);
CREATE INDEX IF NOT EXISTS idx_shop_id ON products_in_shops (shop_id);
CREATE INDEX IF NOT EXISTS idx_shop_name ON shops (name);
CREATE INDEX IF NOT EXISTS idx_city_id ON shops (city_id);
CREATE INDEX IF NOT EXISTS idx_street_id ON shops (street_id);
CREATE INDEX IF NOT EXISTS idx_number_id ON shops (number_id);


DROP INDEX IF EXISTS idx_category_id;
DROP INDEX IF EXISTS idx_product_id;

DROP INDEX IF EXISTS idx_category_name;
DROP INDEX IF EXISTS idx_shop_id;
DROP INDEX IF EXISTS idx_shop_name;
DROP INDEX IF EXISTS idx_city_id;
DROP INDEX IF EXISTS idx_street_id;
DROP INDEX IF EXISTS idx_number_id;


SELECT pg_size_pretty(pg_total_relation_size('products_in_shops')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('products')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('category')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('shops')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('city')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('street')) AS total_size;
SELECT pg_size_pretty(pg_total_relation_size('number')) AS total_size;
