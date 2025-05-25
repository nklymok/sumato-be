package com.naz_desu.sumato.api.student.kanji.profile.service;

import com.naz_desu.sumato.common.exception.SumatoException;
import com.naz_desu.sumato.api.student.kanji.profile.dao.SumatoUserProfileDao;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UpdateUserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SumatoUserProfileService {
    private final SumatoUserProfileDao userProfileDao;

    @Transactional
    public UserProfileDTO getUserProfile(Long userId) {
        UserProfileDTO profile = userProfileDao.findDtoByUserId(userId)
                .orElseThrow(() -> new SumatoException("User with ID {} not found!", userId));
        int level = computeLevel(profile.getDangoCount());
        if (profile.getLevel() != level) {
            userProfileDao.updateLevel(userId, level);
            return userProfileDao.findDtoByUserId(userId)
                    .orElseThrow(() -> new SumatoException("User with ID {} not found!", userId));
        }
        return profile;
    }

    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileDTO updateUserProfileDTO) {
        userProfileDao.updateName(userId, updateUserProfileDTO.name());
    }

    private int computeLevel(int dangoCount) {
        if (dangoCount >= 2000) {
            return 1;
        } else if (dangoCount >= 1000) {
            return 2;
        } else if (dangoCount >= 600) {
            return 3;
        } else if (dangoCount >= 300) {
            return 4;
        } else {
            return 5;
        }
    }
}
