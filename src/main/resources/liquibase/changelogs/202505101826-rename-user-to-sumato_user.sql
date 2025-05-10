-- liquibase formatted sql
-- changeset nazariyklymok:20250510-rename-user-to-sumato_user


ALTER TABLE "user" RENAME TO sumato_user;
ALTER SEQUENCE user_id_seq RENAME TO sumato_user_id_seq;

