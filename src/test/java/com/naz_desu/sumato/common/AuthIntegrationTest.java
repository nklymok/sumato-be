package com.naz_desu.sumato.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * REQ-3: Any request without a valid token should return HTTP 401 Unauthorized.
 */
@WebMvcTest(com.naz_desu.sumato.api.student.kanji.KanjiController.class)
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private com.naz_desu.sumato.api.student.kanji.study.service.KanjiStudyService kanjiStudyService;
    @MockBean
    private com.naz_desu.sumato.api.student.kanji.review.service.KanjiReviewService kanjiReviewService;
    @MockBean
    private com.naz_desu.sumato.api.student.kanji.answer.KanjiAnswerService kanjiAnswerService;
    @MockBean
    private com.naz_desu.sumato.api.student.kanji.srs.NewKanjiService newKanjiService;

    @Test
    void requestWithoutTokenShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/student/kanji/1/study"))
                .andExpect(status().isUnauthorized());
    }
}
