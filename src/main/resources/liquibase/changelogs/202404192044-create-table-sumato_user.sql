--liquibase formatted sql

-- changeset naz:1619323062138-1
CREATE TABLE sumato_user(
    id SERIAL PRIMARY KEY,
    auth_id VARCHAR(255) NOT NULL UNIQUE,
    public_id VARCHAR(255) NOT NULL UNIQUE,
    registered_at TIMESTAMPTZ NOT NULL,
    ip_address VARCHAR(255) NULL,
    ip_address_country VARCHAR(255) NULL
);
