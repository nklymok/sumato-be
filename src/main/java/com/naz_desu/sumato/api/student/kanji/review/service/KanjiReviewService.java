package com.naz_desu.sumato.api.student.kanji.review.service;

import com.naz_desu.sumato.api.student.kanji.KanjiUtil;
import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KanjiReviewService {
    private final UserKanjiDao userKanjiDao;

    public int getKanjiToReviewCount(Long userId) {
        return userKanjiDao.getKanjiToReviewCount(userId, Instant.now());
    }

    public List<KanjiDto> getKanjiToReview(Long userId) {
        return userKanjiDao.getKanjiReviews(userId, Instant.now())
                .stream().map(KanjiUtil::mapKanjiReviewToKanjiDto)
                .toList();
    }

}
