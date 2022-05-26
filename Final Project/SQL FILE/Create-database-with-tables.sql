
CREATE DATABASE banking_system;
USE banking_system;

CREATE TABLE accounts (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

CREATE TABLE savings(
id INT AUTO_INCREMENT PRIMARY KEY,
balance DOUBLE,
account_id INT,
FOREIGN KEY (account_id) REFERENCES accounts(id)
);
ALTER TABLE accounts AUTO_INCREMENT = 1001;

CREATE TABLE current(
id INT AUTO_INCREMENT PRIMARY KEY,
balance DOUBLE,
account_id INT,
FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE checking(
id INT AUTO_INCREMENT PRIMARY KEY,
balance DOUBLE,
account_id INT,
FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE transactions (
id INT AUTO_INCREMENT PRIMARY KEY,
modified_date DATETIME DEFAULT NOW(),
transaction_type VARCHAR(10) NOT NULL,
type_of_account VARCHAR(10) NOT NULL,
amount DOUBLE NOT NULL,
account_id INT,
FOREIGN KEY (account_id) REFERENCES accounts(id)
);
ALTER TABLE transactions AUTO_INCREMENT = 10001;

CREATE TABLE cards(
id INT AUTO_INCREMENT PRIMARY KEY,
pin_code VARCHAR(6),
account_id INT,
FOREIGN KEY (account_id) REFERENCES accounts(id)
);



SHOW TABLES;
