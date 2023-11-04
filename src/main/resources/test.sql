SELECT shop_id, SUM(quantity) AS quantity
FROM products_in_shops
WHERE product_id IN (SELECT id FROM products WHERE category_id = (SELECT id FROM category WHERE name = 'Одяг'))
GROUP BY shop_id
ORDER BY SUM(quantity) DESC
LIMIT 1;

SELECT s.id AS shop_id, s.name AS shop_name, c.name AS city, st.name AS street, n.name AS number, SUM(ps.quantity) AS quantity
FROM shops s
         INNER JOIN products_in_shops ps ON s.id = ps.shop_id
         INNER JOIN products p ON ps.product_id = p.id
         INNER JOIN category cat ON p.category_id = cat.id
         INNER JOIN city c ON s.city_id = c.id
         INNER JOIN street st ON s.street_id = st.id
         INNER JOIN number n ON s.number_id = n.id
WHERE cat.name = 'Одяг'
GROUP BY s.id, s.name, c.name, st.name, n.name
ORDER BY SUM(ps.quantity) DESC
LIMIT 1;


WITH max_quantity_shop AS (
    SELECT shop_id, SUM(quantity) AS quantity
    FROM products_in_shops
    WHERE product_id IN (SELECT id FROM products WHERE category_id = (SELECT id FROM category WHERE name = 'Одяг'))
    GROUP BY shop_id
    ORDER BY SUM(quantity) DESC
    LIMIT 1
)
SELECT ms.shop_id, s.name AS shop_name, c.name AS city, st.name AS street, n.name AS number, ms.quantity
FROM max_quantity_shop ms
         JOIN shops s ON ms.shop_id = s.id
         JOIN city c ON s.city_id = c.id
         JOIN street st ON s.street_id = st.id
         JOIN number n ON s.number_id = n.id;


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
