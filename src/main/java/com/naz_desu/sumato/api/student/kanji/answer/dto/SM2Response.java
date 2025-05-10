package com.naz_desu.sumato.api.student.kanji.answer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record SM2Response(@JsonProperty("ease_factor") double easiness, int interval, int repetitions, @JsonProperty("review_datetime") Instant reviewDate) { }
