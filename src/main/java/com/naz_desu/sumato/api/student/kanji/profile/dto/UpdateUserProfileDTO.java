package com.naz_desu.sumato.api.student.kanji.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileDTO(
    @NotBlank(message = "\"name\" must be present and not blank.")
    @Size(min = 3, max = 20, message = "\"name\" must be between {min} and {max} characters long.")
    @Pattern(regexp = "\\w+", message = "\"name\" must contain only Latin letters, digits, and underscores.")
    String name
) {
}
