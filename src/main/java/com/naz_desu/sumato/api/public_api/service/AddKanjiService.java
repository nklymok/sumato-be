package com.naz_desu.sumato.api.public_api.service;

import com.naz_desu.sumato.api.public_api.dao.KanjiDao;
import com.naz_desu.sumato.api.public_api.dto.KanjiDto;
import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddKanjiService {

    private final KanjiDao kanjiDao;

    public void addKanji(KanjiDto kanjiDto) {
        Kanji kanji = new Kanji();
        kanji.setValue(kanjiDto.value());
        kanji.setMeaning(kanjiDto.meaning());
        kanji.setKunyomi(kanjiDto.kunyomi());
        kanji.setOnyomi(kanjiDto.onyomi());
        kanji.setKoohiiStory(kanjiDto.koohiiStory());
        kanji.setGrade(kanjiDto.grade());
        kanji.setFrequency(kanjiDto.frequency());

        kanjiDao.save(kanji);
    }

}
