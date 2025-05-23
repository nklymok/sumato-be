package com.naz_desu.sumato.api.student.kanji.entity;

import com.naz_desu.sumato.common.entity.SumatoUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_kanji_review")
public class KanjiReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private SumatoUser user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Kanji kanji;
    private boolean isFirstReview;
    private Instant reviewedAt;
    private Instant nextReviewAt;
    private Integer dangoEarned;
    private Double easiness;
    private Integer interval;
    private Integer repetitions;
}

