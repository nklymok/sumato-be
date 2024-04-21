package com.naz_desu.sumato.kanji.study.service;

import com.naz_desu.sumato.kanji.KanjiUtil;
import com.naz_desu.sumato.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.kanji.dto.KanjiDto;
import com.naz_desu.sumato.kanji.dto.KanjiProjection;
import com.naz_desu.sumato.kanji.entity.Kanji;
import com.naz_desu.sumato.kanji.entity.KanjiExample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KanjiStudyService {
    private final UserKanjiDao userKanjiDao;

    public int getKanjiToStudyCount(Long userId) {
        return userKanjiDao.getKanjiToStudyCount(userId);
    }

    public List<KanjiDto> getKanjiToStudy(Long userId) {
        return userKanjiDao.getKanjiToStudy(userId).stream()
                .map(KanjiUtil::mapKanjiToDto)
                .toList();
    }


    public Integer getLearnedKanjiCount(Long userId) {
        return userKanjiDao.getLearnedKanjiCount(userId);
    }
}
