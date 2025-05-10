package com.naz_desu.sumato.api.student.kanji.answer.dto;

import java.time.Instant;

public record SM2Data(double easiness, int interval, int repetitions, Instant reviewDate) { }
