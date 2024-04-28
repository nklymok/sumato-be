package com.naz_desu.sumato.api.student.kanji.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sumato_kanji")
public class Kanji {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String onyomi;
    private String kunyomi;
    private String meaning;
    private String koohiiStory;
    private String grade;
    private Integer frequency;
    @OneToMany(mappedBy = "kanji", fetch = FetchType.LAZY)
    private List<KanjiExample> examples;
}
