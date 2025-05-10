-- liquibase formatted sql
-- changeset nazariyklymok:20250510-fix-sm2-metadata

update user_kanji_review set interval = 1 where interval = 0;
update user_kanji_review set easiness= 2.5 where easiness = 0;

alter table user_kanji_review add constraint review_interval_over_zero check (interval > 0);
