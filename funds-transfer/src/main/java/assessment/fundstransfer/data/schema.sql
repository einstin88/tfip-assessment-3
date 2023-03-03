CREATE DATABASE IF NOT EXISTS acme_bank;

USE acme_bank;

CREATE TABLE IF NOT EXISTS accounts(
    account_id VARCHAR(10) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    balance DECIMAL(20, 2) NOT NULL,
    PRIMARY KEY (account_id)
);

INSERT INTO accounts(account_id, first_name, balance)
VALUES ("V9L3Jd1BBI", "fred", 100.00),
    ("fhRq46Y6vB", "barney", 300.00),
    ("uFSFRqUpJy", "wilma", 1000.00),
    ("ckTV56axff", "betty", 1000.00),
    ("Qgcnwbshbh", "pebbles", 50.00),
    ("if9l185l18", "bambam", 50.00);