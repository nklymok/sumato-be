package com.naz_desu.sumato.api.student.kanji.srs;

import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.naz_desu.sumato.constants.SumatoConstants.MAX_NEW_KANJI_DAILY;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewKanjiService {
    private final UserKanjiDao userKanjiDao;

    @Transactional
    public void assignNewKanji(Long userId) {
        Instant toReviewAt = Instant.now();
        int amount = Math.max(0, MAX_NEW_KANJI_DAILY - userKanjiDao.getKanjiToStudyCount(userId));
        log.info("Assigning {} new kanji to user {}", amount, userId);
        userKanjiDao.assignNewKanjiToUser(userId, amount, toReviewAt);
    }

    @Transactional
    public void assignNewKanji(Long userId, int amount) {
        Instant toReviewAt = Instant.now();
        log.info("Assigning {} new kanji to user {}", amount, userId);
        userKanjiDao.assignNewKanjiToUser(userId, amount, toReviewAt);
    }

}
