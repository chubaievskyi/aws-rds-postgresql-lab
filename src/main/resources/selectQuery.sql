WITH max_quantity_shop AS (
    SELECT shop_id, SUM(quantity) AS quantity
    FROM products_in_shops
    WHERE product_id IN (SELECT id FROM products WHERE category_id = (SELECT id FROM category WHERE name = ?))
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