--liquibase formatted sql

-- changeset naz:1619323062143-1
ALTER TABLE sumato_user_profile
    ADD CONSTRAINT jlpt_level_range_check CHECK (jlpt_level BETWEEN 1 AND 5);

-- changeset naz:1619323062143-2
ALTER TABLE sumato_user_kanji_review
    ADD CONSTRAINT dango_earned_check CHECK (dango_earned IS NULL OR dango_earned IN (1, 2));

-- changeset naz:1619323062143-3
ALTER TABLE sumato_user
    ADD CONSTRAINT ip_country_not_ru_check CHECK (ip_address_country IS NULL OR ip_address_country != 'RU');


