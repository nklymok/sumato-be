package com.naz_desu.sumato.api.student.kanji.srs;

import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.profile.dao.SumatoUserProfileDao;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.common.entity.userProfile.UserProfile;
import com.naz_desu.sumato.common.exception.SumatoException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.naz_desu.sumato.common.constants.SumatoConstants.MAX_NEW_KANJI_DAILY;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewKanjiService {
    private final UserKanjiDao userKanjiDao;
    private final SumatoUserProfileDao userProfileDao;


    @Transactional
    public void tryUnlockNewKanji(Long userId) {
        log.info("Trying to unlock new kanji for user {}", userId);
        Instant now = Instant.now();
        UserProfileDTO profile = userProfileDao.findDtoByUserId(userId)
                .orElseThrow(() -> new SumatoException("User profile not found, user id: " + userId + "."));

        if (now.isBefore(profile.getLastUnlockAt().plus(Duration.ofHours(24)))) {
            log.info("User {} has already unlocked some kanji today", userId);
            return;
        }
        ZoneId kyiv = ZoneId.of("Europe/Kyiv");
        Instant startOfDay = LocalDate.now(kyiv)
                .atStartOfDay(kyiv)
                .toInstant();

        int todayUnlocked = userKanjiDao.countUnlocks(userId, startOfDay, now);
        int mastered = userKanjiDao.countMasteredSince(userId, profile.getLastUnlockAt(), 1);

        int canUnlock = Math.min(mastered, MAX_NEW_KANJI_DAILY - todayUnlocked);
        if (canUnlock <= 0) {
            log.info("User {} has no kanji to unlock today. Today unlocked: {}, mastereed since unlock: {}, can unlock: {}", userId, todayUnlocked, mastered, canUnlock);
            return;
        }

        userKanjiDao.assignNewKanjiToUser(userId, canUnlock, now);
        userProfileDao.updateLastUnlockAt(userId, now);
    }

    @Transactional
    public void assignNewKanji(Long userId, int amount) {
        Instant toReviewAt = Instant.now();
        log.info("Assigning {} new kanji to user {}", amount, userId);
        userKanjiDao.assignNewKanjiToUser(userId, amount, toReviewAt);
    }

}
