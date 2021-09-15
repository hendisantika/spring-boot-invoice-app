/* CLIENTS */
INSERT INTO clients(id, name, surname, email, created_at, photo)
VALUES (1, 'Uzumaki', 'Naruto', 'user1@gmail.com', CURRENT_DATE, ''),
       (2, 'Uchiha', 'Sasuke', 'user2@gmail.com', CURRENT_DATE, ''),
       (3, 'Haruno', 'Sakura', 'user3@gmail.com', CURRENT_DATE, ''),
       (4, 'Hatake', 'Kakashi', 'user4@gmail.com', CURRENT_DATE, ''),
       (5, 'Hyuuga', 'Neji', 'user5@gmail.com', CURRENT_DATE, ''),
       (6, 'Rock', 'Lee', 'user6@gmail.com', CURRENT_DATE, ''),
       (7, 'Ten-ten', 'China', 'user7@gmail.com', CURRENT_DATE, ''),
       (8, 'Aburame', 'Shino', 'user8@gmail.com', CURRENT_DATE, ''),
       (9, 'Hyuuga', 'Hinata', 'user9@gmail.com', CURRENT_DATE, ''),
       (10, 'Inuzuka', 'Kiba', 'user10@gmail.com', CURRENT_DATE, ''),
       (11, 'Naara', 'Shikamaru', 'user11@gmail.com', CURRENT_DATE, ''),
       (12, 'Yamanaka', 'Ino', 'user12@gmail.com', CURRENT_DATE, ''),
       (13, 'Akimichi', 'Choji', 'user13@gmail.com', CURRENT_DATE, ''),
       (14, 'Asuma', 'Sarutobi', 'user14@gmail.com', CURRENT_DATE, ''),
       (15, 'Yuuha', 'Kurenai', 'user15@gmail.com', CURRENT_DATE, ''),
       (16, 'Sarutobi', 'Hiruzen', 'user16@gmail.com', CURRENT_DATE, '');


/* PRODUCTS */
INSERT INTO products(name, price, created_at)
VALUES ('Television Panasonic LCD', 159, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Camera Sony DSC-W320', 329, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Smartphone Samsung Galaxy S13', 899, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Smartphone iPhone 12X', 1299, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Smartphone LG G5', 450, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Xiaomi Note 12', 90, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Xiaomi Redmi 10', 465, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Vivo Reno 5', 349, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Oppo F11', 29, NOW());
INSERT INTO products(name, price, created_at)
VALUES ('Huawei', 2, NOW());

/* INVOICES */
INSERT INTO invoices(description, information, client_id, created_at)
VALUES ('Office equipments', NULL, 1, NOW());
INSERT INTO invoice_lines(quantity, invoice_id, product_id)
VALUES (1, 1, 1);
INSERT INTO invoice_lines(quantity, invoice_id, product_id)
VALUES (2, 1, 4);
INSERT INTO invoice_lines(quantity, invoice_id, product_id)
VALUES (3, 1, 8);
INSERT INTO invoice_lines(quantity, invoice_id, product_id)
VALUES (5, 1, 6);

/* USERS */
INSERT INTO USERS(USERNAME, PASSWORD, ENABLED)
VALUES ('admin', '$2a$10$mlaWy6IDZA6y5ygXfNmcKOCANYYOWGpBHKmVNu860OoJy7E7jujRG', 1); -- 12345
INSERT INTO USERS(USERNAME, PASSWORD, ENABLED)
VALUES ('user', '$2a$10$UTaaXxOa70jZ0rlAxPpcE.SfVFesa6FDPtYhftnT76B3ODfP1v6gC', 1);
-- 12345

/* ROLES */
INSERT INTO AUTHORITIES(USER_ID, AUTHORITY)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO AUTHORITIES(USER_ID, AUTHORITY)
VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITIES(USER_ID, AUTHORITY)
VALUES (2, 'ROLE_USER');
