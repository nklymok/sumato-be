package com.naz_desu.sumato.api.student.kanji.profile.service;

import com.naz_desu.sumato.api.student.kanji.profile.dao.SumatoUserProfileDao;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UpdateUserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.common.exception.SumatoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for profile service covering:
 * REQ-2: Automatic computation and update of JLPT level.
 * REQ-3: Validation of nickname constraints (backend validation layer).
 */
@ExtendWith(MockitoExtension.class)
class SumatoUserProfileServiceTest {

    @Mock
    private SumatoUserProfileDao userProfileDao;

    @InjectMocks
    private SumatoUserProfileService service;

    @Test
    void getUserProfileShouldComputeAndUpdateLevel() {
        UserProfileDTO original = new UserProfileDTO() {
            public Long getId() { return 1L; }
            public Integer getDangoCount() { return 600; }
            public String getName() { return "Alice"; }
            public Integer getLevel() { return 5; }
            public String getPublicId() { return "pub"; }
            public Instant getLastUnlockAt() { return Instant.now(); }
        };
        UserProfileDTO updatedDto = new UserProfileDTO() {
            public Long getId() { return 1L; }
            public Integer getDangoCount() { return 600; }
            public String getName() { return "Alice"; }
            public Integer getLevel() { return 3; }
            public String getPublicId() { return "pub"; }
            public Instant getLastUnlockAt() { return Instant.now(); }
        };
        when(userProfileDao.findDtoByUserId(1L))
                .thenReturn(Optional.of(original), Optional.of(updatedDto));

        UserProfileDTO updated = service.getUserProfile(1L);
        verify(userProfileDao).updateLevel(1L, 3);
        assertEquals(3, updated.getLevel());
    }

    @Test
    void updateUserProfileShouldCallDao() {
        UpdateUserProfileDTO dto = new UpdateUserProfileDTO("Alice_01");
        service.updateUserProfile(1L, dto);
        verify(userProfileDao).updateName(1L, "Alice_01");
    }

    @Test
    void getUserProfileShouldThrowWhenNotFound() {
        when(userProfileDao.findDtoByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(SumatoException.class, () -> service.getUserProfile(1L));
    }
}