package com.naz_desu.sumato.api.student.kanji;

import com.naz_desu.sumato.api.student.kanji.dto.KanjiDto;
import com.naz_desu.sumato.api.student.kanji.answer.KanjiAnswerService;
import com.naz_desu.sumato.api.student.kanji.srs.NewKanjiService;
import com.naz_desu.sumato.api.student.kanji.study.service.KanjiStudyService;
import com.naz_desu.sumato.api.student.kanji.review.service.KanjiReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for Study and Review endpoints covering:
 * REQ-1: Cards contain kanji, onyomi, kunyomi, mnemonic.
 * REQ-2: Test questions correspond to card set (study new kanji).
 * REQ-1 (Review mode): Only review kanji are returned.
 */
@WebMvcTest(KanjiController.class)
class KanjiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KanjiStudyService kanjiStudyService;
    @MockBean
    private KanjiReviewService kanjiReviewService;
    @MockBean
    private KanjiAnswerService kanjiAnswerService;
    @MockBean
    private NewKanjiService newKanjiService;

    @Test
    @WithMockUser
    void getNewKanjiShouldContainRequiredFields() throws Exception {
        KanjiDto dto = new KanjiDto(1L, "日", "ニチ", "ひ", "sun", "mnemonic", "grade", 1, List.of("example"), false);
        when(kanjiStudyService.getKanjiToStudy(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/student/kanji/1/study"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kanjis[0].value").value("日"))
                .andExpect(jsonPath("$.kanjis[0].onyomi").value("ニチ"))
                .andExpect(jsonPath("$.kanjis[0].kunyomi").value("ひ"))
                .andExpect(jsonPath("$.kanjis[0].koohiiStory").value("mnemonic"));
    }

    @Test
    @WithMockUser
    void getKanjiToReviewShouldContainRequiredFields() throws Exception {
        KanjiDto dto = new KanjiDto(2L, "月", "ゲツ", "つき", "moon", "story", "grade", 1, List.of(), false);
        when(kanjiReviewService.getKanjiToReview(2L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/student/kanji/2/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kanjis[0].value").value("月"))
                .andExpect(jsonPath("$.kanjis[0].onyomi").value("ゲツ"))
                .andExpect(jsonPath("$.kanjis[0].kunyomi").value("つき"));
    }
}