package com.naz_desu.sumato.api.student.kanji.answer.api_client;

import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Data;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Answer;
import com.naz_desu.sumato.api.student.kanji.answer.dto.SM2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SM2ApiClient {
    private final WebClient webClient;
    private final Duration timeout = Duration.ofSeconds(2);

    public SM2Data sendAnswer(SM2Answer answer) {
        SM2Response resp = webClient.post()
                .uri("/")
                .bodyValue(answer)
                .retrieve()
                .bodyToMono(SM2Response.class)
                .timeout(timeout)
                .block();         // or use Reactor end-to-end

        return new SM2Data(
                resp.easiness(),
                resp.interval(),
                resp.repetitions(),
                resp.reviewDate()
        );
    }
}

