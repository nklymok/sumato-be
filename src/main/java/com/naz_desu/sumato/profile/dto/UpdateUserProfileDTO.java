package com.naz_desu.sumato.profile.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserProfileDTO(@NotBlank(message = "\"name\" must be present and not blank.") String name) {
}
