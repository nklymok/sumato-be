package com.naz_desu.sumato.api.public_api.dto;

import jakarta.validation.constraints.NotBlank;

public record KanjiDto(@NotBlank String value, String onyomi, String kunyomi, @NotBlank String meaning, String koohiiStory, String grade, Integer frequency) { }
