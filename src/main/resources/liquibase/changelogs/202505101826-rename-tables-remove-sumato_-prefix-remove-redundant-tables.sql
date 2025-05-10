-- liquibase formatted sql
-- changeset nazariyklymok:20250510-strip_prefix
-- comment: Drop tutor logic, drop bookmarks, and rename tables by stripping the 'sumato_' prefix (no pluralization).

-- === Drop obsolete tutor tables ===
DROP TABLE IF EXISTS sumato_tutor_user_notes CASCADE;
DROP TABLE IF EXISTS sumato_tutor_user CASCADE;
DROP TABLE IF EXISTS sumato_tutor CASCADE;

-- === Drop bookmarks table ===
DROP TABLE IF EXISTS sumato_user_kanji_bookmarked CASCADE;

-- === Rename core tables ===
-- Note: "user" is a reserved word, so we quote it.
ALTER TABLE sumato_user RENAME TO "user";
ALTER SEQUENCE sumato_user_id_seq RENAME TO user_id_seq;
ALTER TABLE "user" RENAME CONSTRAINT ip_country_not_ru_check TO user_ip_country_not_ru_check;

ALTER TABLE sumato_user_profile       RENAME TO user_profile;

ALTER TABLE sumato_chat_history       RENAME TO chat_history;
ALTER SEQUENCE sumato_chat_history_id_seq RENAME TO chat_history_id_seq;

ALTER TABLE sumato_kanji              RENAME TO kanji;
ALTER SEQUENCE sumato_kanji_id_seq         RENAME TO kanji_id_seq;

ALTER TABLE sumato_kanji_example      RENAME TO kanji_example;
ALTER SEQUENCE sumato_kanji_example_id_seq RENAME TO kanji_example_id_seq;

ALTER TABLE sumato_user_kanji_review  RENAME TO user_kanji_review;
ALTER SEQUENCE sumato_user_kanji_review_id_seq RENAME TO user_kanji_review_id_seq;

ALTER TABLE sumato_user_kanji_stats   RENAME TO user_kanji_stats;
ALTER SEQUENCE sumato_user_kanji_stats_id_seq RENAME TO user_kanji_stats_id_seq;

