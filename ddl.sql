INSERT INTO roles (id, role_name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');


INSERT INTO users (id, date_of_birth, email, firstname, lastname, password, username)
VALUES 
(1, '1990-01-01', 'example1@example.com', 'John', 'Doe', 'password1', 'johndoe'),
(2, '1995-02-15', 'example2@example.com', 'Jane', 'Smith', 'password2', 'janesmith'),
(3, '1985-07-10', 'example3@example.com', 'Michael', 'Johnson', 'password3', 'michaeljohnson'),
(5, '1985-07-10', 'example4@example.com', 'Michael', 'Johnson', 'password3', 'michaeljohnson4');

INSERT INTO user_roles (user_id, role_id)
values
(5,2);

-- Insert orders
INSERT INTO orders (user_id, ordered_at) VALUES
  (1, '2023-06-28 10:00:00'),
  (2, '2023-06-29 09:30:00');

-- Insert products
INSERT INTO products (slug, name, reference, price, vat, stockable) VALUES
  ('product-1', 'Product 1', 'REF-001', 10.99, 0.2, TRUE),
  ('product-2', 'Product 2', 'REF-002', 19.99, 0.2, FALSE);

-- Insert stocks
INSERT INTO stocks (product_id, quantity, updated_at) VALUES
  (1, 100, '2023-06-28 09:00:00'),
  (2, 50, '2023-06-29 10:30:00');

INSERT INTO product_order (product_id, order_id, quantity, price, vat, created_at) VALUES
  (1, 1, 2, 10.99, 0.2, '2023-06-28 09:15:00'),
  (2, 1, 1, 19.99, 0.2, '2023-06-28 09:20:00'),
  (1, 2, 4, 5.99, 0.1,  '2023-06-29 11:00:00');
