--liquibase formatted sql

-- changeset naz:1619323062141-1
CREATE TABLE sumato_kanji(
    id SERIAL PRIMARY KEY,
    value VARCHAR(1) NOT NULL,
    onyomi VARCHAR(255),
    kunyomi VARCHAR(255),
    meaning VARCHAR(255),
    koohii_story TEXT,
    frequency INT,
    grade VARCHAR(2)
);

-- changeset naz:1619323062141-2
CREATE TABLE sumato_kanji_example(
    id SERIAL PRIMARY KEY,
    kanji_id INT NOT NULL,
    example TEXT NOT NULL,
    FOREIGN KEY (kanji_id) REFERENCES sumato_kanji(id)
);

-- changeset naz:1619323062141-3
CREATE TABLE sumato_user_kanji_bookmarked(
    user_id INT NOT NULL,
    kanji_id INT NOT NULL,
    PRIMARY KEY (user_id, kanji_id),
    FOREIGN KEY (user_id) REFERENCES sumato_user(id),
    FOREIGN KEY (kanji_id) REFERENCES sumato_kanji(id)
);

-- changeset naz:1619323062141-4
CREATE TABLE sumato_user_kanji_review(
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    kanji_id INT NOT NULL,
    is_first_review BOOLEAN NOT NULL,
    reviewed_at TIMESTAMPTZ,
    next_review_at TIMESTAMPTZ,
    dango_earned INT,
    FOREIGN KEY (user_id) REFERENCES sumato_user(id),
    FOREIGN KEY (kanji_id) REFERENCES sumato_kanji(id)
);

-- changeset naz:1619323062141-5
CREATE TABLE sumato_user_kanji_stats(
    id SERIAL PRIMARY KEY,
    kanji_review_id INT NOT NULL,
    easiness DOUBLE PRECISION NOT NULL,
    interval INT NOT NULL,
    repetitions INT NOT NULL,
    FOREIGN KEY (kanji_review_id) REFERENCES sumato_user_kanji_review(id)
);
