package com.naz_desu.sumato.kanji.review.service;

import com.naz_desu.sumato.kanji.KanjiUtil;
import com.naz_desu.sumato.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.kanji.dto.KanjiDto;
import com.naz_desu.sumato.kanji.dto.KanjiProjection;
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
        return userKanjiDao.getKanjiToReview(userId, Instant.now())
                .stream().map(KanjiUtil::mapKanjiToDto)
                .toList();
    }

}
