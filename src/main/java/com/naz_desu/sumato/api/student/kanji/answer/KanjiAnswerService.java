package com.naz_desu.sumato.api.student.kanji.answer;

import com.naz_desu.sumato.api.student.kanji.dao.ReviewLogDao;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiAnswer;
import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import com.naz_desu.sumato.api.student.kanji.answer.api_client.SM2ApiClient;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Data;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Answer;
import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;
import com.naz_desu.sumato.api.student.kanji.entity.ReviewLog;
import com.naz_desu.sumato.common.exception.SumatoException;
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
    private final ReviewLogDao reviewLogDao;
    private final SM2ApiClient sm2ApiClient;

    @Transactional
    public Boolean checkAnswer(KanjiAnswer kanjiAnswer) {
        KanjiReview kanjiReview = userKanjiDao.findById(kanjiAnswer.reviewId())
                .orElseThrow(() -> new SumatoException("Kanji Review with id {} not found", kanjiAnswer.reviewId()));
        boolean isCorrect = checkMeanings(kanjiAnswer, kanjiReview.getKanji());
        SM2Data newStats = calculateSM2Metadata(kanjiReview, isCorrect);
        addReviewLog(kanjiReview, isCorrect);
        updateKanjiReview(newStats, kanjiAnswer.reviewId());
        return isCorrect;

    }

    private void addReviewLog(KanjiReview kanjiReview, boolean isCorrect) {
        ReviewLog reviewLog = new ReviewLog()
                .setReview(kanjiReview)
                .setIsCorrect(isCorrect)
                .setReviewedAt(Instant.now());
        reviewLogDao.save(reviewLog);
    }

    private SM2Data calculateSM2Metadata(KanjiReview review, boolean isCorrect) {
        SM2Answer answer = new SM2Answer(isCorrect,
                review.getEasiness(),
                review.getInterval(),
                review.getRepetitions()
        );
        return sm2ApiClient.sendAnswer(answer);
    }

    private boolean checkMeanings(KanjiAnswer kanjiAnswer, Kanji kanji) {
        return Arrays.stream(kanji.getMeaning().split(","))
                .map(String::trim)
                .anyMatch(meaning -> meaning.equalsIgnoreCase(kanjiAnswer.answer()));
    }

    private void updateKanjiReview(SM2Data sm2data, long reviewId) {
        KanjiReview kanjiReview = userKanjiDao.findById(reviewId)
                .orElseThrow(() -> new SumatoException("Review with id {} not found", reviewId));
        kanjiReview.setReviewedAt(Instant.now());
        kanjiReview.setNextReviewAt(sm2data.reviewDate());
        kanjiReview.setFirstReview(false);
        kanjiReview.setInterval(sm2data.interval());
        kanjiReview.setEasiness(sm2data.easiness());
        kanjiReview.setRepetitions(sm2data.repetitions());
        userKanjiDao.save(kanjiReview);
        userKanjiDao.save(kanjiReview);
    }

}
