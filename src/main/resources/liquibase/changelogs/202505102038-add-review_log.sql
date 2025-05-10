-- liquibase formatted sql
-- changeset nazariyklymok:20250510-add-review_log

CREATE TABLE review_log (
    id SERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    reviewed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES user_kanji_review(id) ON DELETE CASCADE
);
