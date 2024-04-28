package com.naz_desu.sumato.api.student.kanji.dto;

import java.util.List;

public interface KanjiProjection {
    Long getReviewId();
    String getValue();
    String getOnyomi();
    String getKunyomi();
    String getMeaning();
    String getKoohiiStory();
    String getGrade();
    Integer getFrequency();
    List<String> getExamples(); // Change return type to List<String>
}
