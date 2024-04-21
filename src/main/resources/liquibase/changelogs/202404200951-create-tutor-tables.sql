--liquibase formatted sql

-- changeset naz:1619323062142-1
CREATE TABLE sumato_tutor(
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL
);

-- changeset naz:1619323062142-2
CREATE TABLE sumato_tutor_user(
    tutor_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (tutor_id, user_id),
    FOREIGN KEY (tutor_id) REFERENCES sumato_tutor(id),
    FOREIGN KEY (user_id) REFERENCES sumato_user(id)
);

-- changeset naz:1619323062142-3
CREATE TABLE sumato_tutor_user_notes(
    id SERIAL PRIMARY KEY,
    tutor_id INT NOT NULL,
    user_id INT NOT NULL,
    note TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (tutor_id, user_id) REFERENCES sumato_tutor_user(tutor_id, user_id)
);

