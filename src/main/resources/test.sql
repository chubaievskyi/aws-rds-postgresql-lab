SELECT shop_id, SUM(quantity) AS quantity
FROM products_in_shops
WHERE product_id IN (SELECT id FROM products WHERE category_id = (SELECT id FROM category WHERE name = 'Садові інструменти'))
GROUP BY shop_id
ORDER BY SUM(quantity) DESC
LIMIT 1;

SELECT s.*, SUM(ps.quantity) AS quantity
FROM shops s
         INNER JOIN products_in_shops ps ON s.id = ps.shop_id
         INNER JOIN products p ON ps.product_id = p.id
         INNER JOIN category c ON p.category_id = c.id
WHERE c.name = ?
GROUP BY s.id, s.name, s.city_id, s.street_id, s.number_id
ORDER BY SUM(ps.quantity) DESC
LIMIT 1;

SELECT s.name AS shop_name, c.name AS city, st.name AS street, n.name AS number, SUM(ps.quantity) AS quantity
FROM shops s
         INNER JOIN products_in_shops ps ON s.id = ps.shop_id
         INNER JOIN products p ON ps.product_id = p.id
         INNER JOIN category cat ON p.category_id = cat.id
         INNER JOIN city c ON s.city_id = c.id
         INNER JOIN street st ON s.street_id = st.id
         INNER JOIN number n ON s.number_id = n.id
WHERE cat.name = ?
GROUP BY s.name, c.name, st.name, n.name
ORDER BY SUM(ps.quantity) DESC
LIMIT 1;


