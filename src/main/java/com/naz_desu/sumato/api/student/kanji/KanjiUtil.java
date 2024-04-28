package com.naz_desu.sumato.api.student.kanji;

import com.naz_desu.sumato.api.student.kanji.dto.KanjiDto;
import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiExample;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;

import java.util.List;
import java.util.stream.Collectors;

public class KanjiUtil {

    public static KanjiDto mapKanjiReviewToKanjiDto(KanjiReview kanjiReview) {
        List<String> examples = kanjiReview.getKanji().getExamples().stream()
                .map(KanjiExample::getExample)
                .collect(Collectors.toList());
        Kanji kanji = kanjiReview.getKanji();

        return new KanjiDto(
                kanjiReview.getId(),
                kanji.getValue(),
                kanji.getOnyomi(),
                kanji.getKunyomi(),
                kanji.getMeaning(),
                kanji.getKoohiiStory(),
                kanji.getGrade(),
                kanji.getFrequency(),
                examples,
                false
        );
    }

}
