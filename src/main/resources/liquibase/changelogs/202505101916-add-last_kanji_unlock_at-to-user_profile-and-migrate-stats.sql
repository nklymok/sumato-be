-- liquibase formatted sql
-- changeset nazariyklymok:20250510-add-last_kanji_unlock_at-to-user-profile


ALTER TABLE user_profile ADD COLUMN last_kanji_unlock_at TIMESTAMPTZ DEFAULT NULL;

-- changeset nazariyklymok:20250510-migrate-stats

ALTER TABLE user_kanji_review
    ADD COLUMN easiness   DOUBLE PRECISION DEFAULT 2.5,
    ADD COLUMN interval   INTEGER         DEFAULT 0,
    ADD COLUMN repetitions INTEGER        DEFAULT 0;

UPDATE user_kanji_review r
SET
    easiness    = s.easiness,
    interval    = s.interval,
    repetitions = s.repetitions
FROM user_kanji_stats s
WHERE r.id = s.kanji_review_id;

ALTER TABLE user_kanji_review
    ALTER COLUMN easiness   SET NOT NULL,
    ALTER COLUMN interval   SET NOT NULL,
    ALTER COLUMN repetitions SET NOT NULL;

DROP TABLE user_kanji_stats;

DELETE FROM user_kanji_review WHERE id NOT IN (
    SELECT MAX(id) from user_kanji_review GROUP BY user_id, kanji_id
);

ALTER TABLE user_kanji_review
    ADD CONSTRAINT uniq_user_kanji_review_user_id_kanji_id
        UNIQUE (user_id, kanji_id);

ALTER TABLE user_profile
    ADD COLUMN last_unlock_at TIMESTAMPTZ DEFAULT NULL;

UPDATE user_profile up
    SET last_unlock_at = su.registered_at
    FROM sumato_user su
    WHERE su.id = up.user_id;
