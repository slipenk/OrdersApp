INSERT INTO products(id, name, price, total_quantity) values (11, 'iPhone 13 Pro', 30000, 100);
INSERT INTO products(id, name, price, total_quantity) values (12, 'iPhone 11', 25000, 80);
INSERT INTO products(id, name, price, total_quantity) values (13, 'iPhone 8', 15000, 50);

INSERT INTO users(username, enabled, password) values ('slipenk', TRUE, '{bcrypt}$2a$10$92YX2d7o/Ar9KPHpnNcd/.iwWnyzcYfRvIzms7l.2YrmahPnj4CAm');
INSERT INTO users(username, enabled, password) values ('salah', TRUE, '{bcrypt}$2a$10$92YX2d7o/Ar9KPHpnNcd/.iwWnyzcYfRvIzms7l.2YrmahPnj4CAm');

INSERT INTO authorities(id, authority, username) values (11, 'ROLE_MANAGER', 'slipenk');
INSERT INTO authorities(id, authority, username) values (12, 'ROLE_CLIENT', 'slipenk');
INSERT INTO authorities(id, authority, username) values (13, 'ROLE_CLIENT', 'salah');

INSERT INTO orders(id, paid, created_date_time, username) values (11, FALSE, NOW(), 'salah');

INSERT INTO order_items(id, quantity, order_id, product_id) values (11, 5, 11, 11);
INSERT INTO order_items(id, quantity, order_id, product_id) values (12, 10, 11, 12);
