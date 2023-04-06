CREATE DATABASE IF NOT EXISTS paf_batch_2;

USE DATABASE paf_batch_2;

SHOW TABLES;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
    user_id VARCHAR(8),
    username VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(30) NULL,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS task;

CREATE TABLE task (
    task_id INT AUTO_INCREMENT,
    user_id VARCHAR(8) NOT NULL,
    description VARCHAR(255) NOT NULL,
    priority enum ("1", "2", "3") NOT NULL,
    due_date date NOT NULL,
    PRIMARY KEY (task_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(user_id)
);