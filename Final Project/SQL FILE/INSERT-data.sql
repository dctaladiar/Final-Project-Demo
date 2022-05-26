USE banking_system;

INSERT INTO accounts (name)
VALUES ("Juan Dela Cruz"), ("Kardo Dalisay"), ("Paraluman");

INSERT INTO cards (pin_code, account_id)
VALUES ("123456", 1001), ("234567", 1002), ("345678", 1003);

INSERT INTO savings (balance, account_id)
VALUES ("3000.00", 1001), ("2000.00", 1002), ("4000.00", 1003);

INSERT INTO checking (balance, account_id)
VALUES ("4000.00", 1001), ("2020.00", 1002), ("11000.00", 1003);
INSERT INTO current (balance, account_id)
VALUES ("0.00", 1001), ("3300.00", 1002), ("5000.00",1003);
