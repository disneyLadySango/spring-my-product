CREATE USER 'product_user'@'localhost' IDENTIFIED BY 'product_user';
grant ALL ON product.* TO 'product_user'@'localhost';
