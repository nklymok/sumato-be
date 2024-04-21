package com.naz_desu.sumato.kanji.entity;

import com.naz_desu.sumato.entity.SumatoUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sumato_user_kanji_stats")
public class KanjiStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //id SERIAL PRIMARY KEY,
    //    user_kanji_review_id INT NOT NULL,
    //    easiness INT NOT NULL,
    //    interval INT NOT NULL,
    //    repetitions INT NOT NULL,
    //    FOREIGN KEY (user_kanji_review_id) REFERENCES sumato_user_kanji_review(id)
    @OneToOne(fetch = FetchType.EAGER)
    private KanjiReview kanjiReview;
    private Double easiness;
    private Integer interval;
    private Integer repetitions;
}

