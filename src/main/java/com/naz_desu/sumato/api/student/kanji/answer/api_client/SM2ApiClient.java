package com.naz_desu.sumato.api.student.kanji.answer.api_client;

import com.naz_desu.sumato.api.student.kanji.answer.dto.KanjiStatsDTO;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Answer;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class SM2ApiClient {
    private final RestTemplate restTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;


    public KanjiStatsDTO sendAnswer(SM2Answer answer) {
        SM2Response sm2Response = restTemplate.postForObject("http://localhost:8081/sm2", answer, SM2Response.class);
        return new KanjiStatsDTO(sm2Response.easiness(), sm2Response.interval(), sm2Response.repetitions(), Instant.from(formatter.parse(sm2Response.reviewDate())));
    }

}
