package com.naz_desu.sumato.api.student.kanji.answer.dto;


public record SM2Answer(boolean correct, Double easiness, Integer interval, Integer repetitions) {
    public SM2Answer(boolean correct) {
        this(correct, null, null, null);
    }
}
