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

    public UserProfileDTO getUserProfile(Long userId) {
        return userProfileDao.findDtoByUserId(userId)
                .orElseThrow(() -> new SumatoException("User with ID {} not found!", userId));
    }

    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileDTO updateUserProfileDTO) {
        userProfileDao.updateName(userId, updateUserProfileDTO.name());
    }
}
