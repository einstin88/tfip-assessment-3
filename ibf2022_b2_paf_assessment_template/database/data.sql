SHOW TABLES;

INSERT INTO user (user_id, username, name)
VALUES ("1b80114c", "fred", "Fred"),
    ("cf66dae3", "wilma", "Wilma"),
    ("a8b9800d", "barney", "Barney"),
    ("66223e28", "betty", "Betty");

-- LOAD data INFILE 'database/data.csv' INTO TABLE user FIELDS TERMINATED by ',' IGNORE 1 ROWS;
INSERT INTO task (user_id, description, priority, due_date)
VALUES (?, ?, ?, ?);