package com.naz_desu.sumato.kanji.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KanjiDto {
    private Long reviewId;
    private String value;
    private String onyomi;
    private String kunyomi;
    private String meaning;
    private String koohiiStory;
    private String grade;
    private Integer frequency;
    private List<String> examples;
}
