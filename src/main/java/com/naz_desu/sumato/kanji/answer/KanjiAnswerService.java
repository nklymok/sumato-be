package com.naz_desu.sumato.kanji.answer;

import com.naz_desu.sumato.kanji.answer.api_client.SM2ApiClient;
import com.naz_desu.sumato.kanji.answer.dto.KanjiStatsDTO;
import com.naz_desu.sumato.kanji.answer.dto.SM2Answer;
import com.naz_desu.sumato.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.kanji.dao.UserKanjiStatsDao;
import com.naz_desu.sumato.kanji.dto.KanjiAnswer;
import com.naz_desu.sumato.kanji.entity.Kanji;
import com.naz_desu.sumato.kanji.entity.KanjiReview;
import com.naz_desu.sumato.kanji.entity.KanjiStats;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class KanjiAnswerService {
    private final UserKanjiDao userKanjiDao;
    private final UserKanjiStatsDao userKanjiStatsDao;
    private final SM2ApiClient sm2ApiClient;

    @Transactional
    public Boolean checkAnswer(Long userId, KanjiAnswer kanjiAnswer) {
        Kanji kanji = userKanjiDao.getKanjiFromReview(userId, kanjiAnswer.reviewId());
        boolean isCorrect = checkMeanings(kanjiAnswer, kanji);
        userKanjiDao.findLatestKanjiStats(userId, kanji.getId())
                .ifPresentOrElse(
                        (kanjiStats) -> createKanjiStats(isCorrect, kanjiStats),
                        () -> createNewKanjiStats(kanjiAnswer, isCorrect)
                );
        return isCorrect;
    }

    private void createNewKanjiStats(KanjiAnswer kanjiAnswer, boolean isCorrect) {
        KanjiStatsDTO newStats = sm2ApiClient.sendAnswer(new SM2Answer(isCorrect));
        KanjiStats newKanjiStats = new KanjiStats();
        newKanjiStats.setEasiness(newStats.easiness());
        newKanjiStats.setInterval(newStats.interval());
        newKanjiStats.setRepetitions(newStats.repetitions());
        KanjiReview proxyReview = new KanjiReview();
        proxyReview.setId(kanjiAnswer.reviewId());
        newKanjiStats.setKanjiReview(proxyReview);
        userKanjiStatsDao.save(newKanjiStats);
        updateOrCreateReview(isCorrect, newStats, kanjiAnswer.reviewId());
    }

    private void createKanjiStats(boolean isCorrect, KanjiStats kanjiStats) {
        KanjiStatsDTO newStats = sm2ApiClient.sendAnswer(
                new SM2Answer(isCorrect,
                        kanjiStats.getEasiness(),
                        kanjiStats.getInterval(),
                        kanjiStats.getRepetitions()
                )
        );
        KanjiStats newKanjiStats = new KanjiStats();
        newKanjiStats.setEasiness(newStats.easiness());
        newKanjiStats.setInterval(newStats.interval());
        newKanjiStats.setRepetitions(newStats.repetitions());
        newKanjiStats.setKanjiReview(kanjiStats.getKanjiReview());
        userKanjiStatsDao.save(newKanjiStats);
        updateOrCreateReview(isCorrect, newStats, kanjiStats.getKanjiReview().getId());
    }

    private boolean checkMeanings(KanjiAnswer kanjiAnswer, Kanji kanji) {
        return Arrays.stream(kanji.getMeaning().split(","))
                .map(String::trim)
                .anyMatch(meaning -> meaning.equalsIgnoreCase(kanjiAnswer.answer()));
    }

    private void updateOrCreateReview(boolean isCorrectAnswer, KanjiStatsDTO kanjiStatsDTO, long reviewId) {
        if (isCorrectAnswer) {
            userKanjiDao.findById(reviewId)
                    .ifPresentOrElse((oldReview) -> {
                        KanjiReview kanjiReview = new KanjiReview();
                        kanjiReview.setKanji(oldReview.getKanji());
                        kanjiReview.setUser(oldReview.getUser());
                        kanjiReview.setReviewedAt(Instant.now());
                        kanjiReview.setNextReviewAt(kanjiStatsDTO.reviewDate());
                        kanjiReview.setFirstReview(false);
                        userKanjiDao.save(kanjiReview);
                    }, () -> {
                        log.error("Review with id {} not found???", reviewId);
                    });
        } else {
            userKanjiDao.updateReviewDates(reviewId, Instant.now(), kanjiStatsDTO.reviewDate());
        }
    }

}
