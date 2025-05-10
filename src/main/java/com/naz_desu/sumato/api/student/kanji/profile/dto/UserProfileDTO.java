package com.naz_desu.sumato.api.student.kanji.profile.dto;

import java.time.Instant;

public interface UserProfileDTO {
    Long getId();
    Integer getDangoCount();
    String getName();
    Integer getLevel();
    String getPublicId();
    Instant getLastUnlockAt();
}
