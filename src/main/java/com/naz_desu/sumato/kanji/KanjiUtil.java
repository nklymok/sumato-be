package com.naz_desu.sumato.kanji;

import com.naz_desu.sumato.kanji.dto.KanjiDto;
import com.naz_desu.sumato.kanji.entity.Kanji;
import com.naz_desu.sumato.kanji.entity.KanjiExample;

import java.util.List;
import java.util.stream.Collectors;

public class KanjiUtil {

    public static KanjiDto mapKanjiToDto(Kanji kanji) {
        List<String> examples = kanji.getExamples().stream()
                .map(KanjiExample::getExample)
                .collect(Collectors.toList());

        return new KanjiDto(
                kanji.getId(),
                kanji.getValue(),
                kanji.getOnyomi(),
                kanji.getKunyomi(),
                kanji.getMeaning(),
                kanji.getKoohiiStory(),
                kanji.getGrade(),
                kanji.getFrequency(),
                examples
        );
    }

}
