package com.naz_desu.sumato.api.student.kanji.study.service;

import com.naz_desu.sumato.api.student.kanji.KanjiUtil;
import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KanjiStudyService {
    private final UserKanjiDao userKanjiDao;

    public int getKanjiToStudyCount(Long userId) {
        return userKanjiDao.getKanjiToStudyCount(userId, Instant.now());
    }

    public List<KanjiDto> getKanjiToStudy(Long userId) {
        return userKanjiDao.getKanjiReviewsToStudy(userId, Instant.now()).stream()
                .map(KanjiUtil::mapKanjiReviewToKanjiDto)
                .toList();
    }


    public Integer getLearnedKanjiCount(Long userId) {
        return userKanjiDao.getLearnedKanjiCount(userId);
    }
}
