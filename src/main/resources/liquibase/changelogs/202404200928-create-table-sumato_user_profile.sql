--liquibase formatted sql

-- changeset naz:1619323062139-1
CREATE TABLE sumato_user_profile(
    user_id INT PRIMARY KEY REFERENCES sumato_user(id),
    name VARCHAR(255),
    jlpt_level INT,
    dango_count INT
);
