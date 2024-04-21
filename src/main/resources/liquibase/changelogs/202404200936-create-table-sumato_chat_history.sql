--liquibase formatted sql

-- changeset naz:1619323062140-1
CREATE TABLE sumato_chat_history(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    message VARCHAR(1024) NOT NULL,
    role VARCHAR(255) NOT NULL,
    sent_at TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES sumato_user(id)
);
