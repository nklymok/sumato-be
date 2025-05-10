package com.naz_desu.sumato.api.student.kanji.dto;

import java.time.Instant;

public record KanjiStatsDto(int kanjiLeftToReview,
                            int kanjiLeftToStudy,
                            int kanjiLearned,
                            Instant nextReviewAt,
                            Instant nextStudyAt) { }
