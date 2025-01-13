INSERT INTO bank_account (id, owner, account_number, balance, create_date) VALUES (1, 'John Doe', '12345', 200.0, CURRENT_TIMESTAMP);
INSERT INTO bank_account (id, owner, account_number, balance, create_date) VALUES (2, 'Jane Smith', '67890', 550.0, CURRENT_TIMESTAMP);

INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (1, CURRENT_TIMESTAMP, 500.0, '67f1aada-637d-4469-a650-3fb6352527b1', 'DEPOSIT', 1);
INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (2, CURRENT_TIMESTAMP, 200.0, '67f1aada-637d-4469-a650-3fb6352527b2', 'WITHDRAW', 1);
INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (3, CURRENT_TIMESTAMP, 100.0, '67f1aada-637d-4469-a650-3fb6352527b3', 'PHONE_BILL_PAYMENT', 1);
INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (4, CURRENT_TIMESTAMP, 1000.0, '67f1aada-637d-4469-a650-3fb6352527b4', 'DEPOSIT', 2);
INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (5, CURRENT_TIMESTAMP, 300.0, '67f1aada-637d-4469-a650-3fb6352527b5', 'WITHDRAW', 2);
INSERT INTO transaction (id, date, amount, approval_code, transaction_type, account_id) VALUES (6, CURRENT_TIMESTAMP, 150.0, '67f1aada-637d-4469-a650-3fb6352527b6', 'PHONE_BILL_PAYMENT', 2);