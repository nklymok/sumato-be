package com.naz_desu.sumato.kanji;

import com.naz_desu.sumato.kanji.answer.KanjiAnswerService;
import com.naz_desu.sumato.kanji.dto.KanjiAnswer;
import com.naz_desu.sumato.kanji.dto.KanjiDto;
import com.naz_desu.sumato.kanji.dto.KanjiProjection;
import com.naz_desu.sumato.kanji.review.service.KanjiReviewService;
import com.naz_desu.sumato.kanji.study.service.KanjiStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kanji")
@RequiredArgsConstructor
public class KanjiController {
    private final KanjiStudyService kanjiStudyService;
    private final KanjiReviewService kanjiReviewService;
    private final KanjiAnswerService kanjiAnswerService;

    @GetMapping("/{userId}/review")
    public Map<String, List<KanjiDto>> getKanjiToReview(@PathVariable Long userId) {
        return Collections.singletonMap("kanjis", kanjiReviewService.getKanjiToReview(userId));
    }

    @GetMapping("/{userId}/study")
    public Map<String, List<KanjiDto>> getNewKanji(@PathVariable Long userId) {
        return Collections.singletonMap("kanjis", kanjiStudyService.getKanjiToStudy(userId));
    }

    @GetMapping("/{userId}/stats")
    public Map<String, Integer> getKanjiToReviewCount(@PathVariable Long userId) {
        return Map.of(
                "kanjiLeftToReview", kanjiReviewService.getKanjiToReviewCount(userId),
                "kanjiLeftToStudy", kanjiStudyService.getKanjiToStudyCount(userId),
                "kanjiLearned", kanjiStudyService.getLearnedKanjiCount(userId)
        );
    }

    @PostMapping("/{userId}/answer")
    public Map<String, Boolean> answerKanji(@PathVariable Long userId, @RequestBody KanjiAnswer answer) {
        return Map.of("isCorrect", kanjiAnswerService.checkAnswer(userId, answer));
    }
}
