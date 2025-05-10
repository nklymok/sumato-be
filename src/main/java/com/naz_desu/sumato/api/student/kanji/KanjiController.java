package com.naz_desu.sumato.api.student.kanji;

import com.naz_desu.sumato.api.student.kanji.answer.KanjiAnswerService;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiAnswer;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiStatsDto;
import com.naz_desu.sumato.api.student.kanji.review.service.KanjiReviewService;
import com.naz_desu.sumato.api.student.kanji.dto.KanjiDto;
import com.naz_desu.sumato.api.student.kanji.srs.NewKanjiService;
import com.naz_desu.sumato.api.student.kanji.study.service.KanjiStudyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/student/kanji")
@RequiredArgsConstructor
public class KanjiController {
    private final KanjiStudyService kanjiStudyService;
    private final KanjiReviewService kanjiReviewService;
    private final KanjiAnswerService kanjiAnswerService;
    private final NewKanjiService newKanjiService;

    @GetMapping("/{userId}/review")
    @PreAuthorize("@securityService.matchesUserId(#userId, authentication.principal)")
    public Map<String, List<KanjiDto>> getKanjiToReview(@PathVariable Long userId) {
        return Collections.singletonMap("kanjis", kanjiReviewService.getKanjiToReview(userId));
    }

    @GetMapping("/{userId}/study")
    @PreAuthorize("@securityService.matchesUserId(#userId, authentication.principal)")
    public Map<String, List<KanjiDto>> getNewKanji(@PathVariable Long userId) {
        return Collections.singletonMap("kanjis", kanjiStudyService.getKanjiToStudy(userId));
    }

    @GetMapping("/{userId}/stats")
    @PreAuthorize("@securityService.matchesUserId(#userId, authentication.principal)")
    public KanjiStatsDto getKanjiToReviewCount(@PathVariable Long userId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        log.info("User with authorities {} requested stats", authorities);
        newKanjiService.tryUnlockNewKanji(userId);
        return new KanjiStatsDto(
                kanjiReviewService.getKanjiToReviewCount(userId),
                kanjiStudyService.getKanjiToStudyCount(userId),
                kanjiStudyService.getLearnedKanjiCount(userId),
                kanjiReviewService.getNextReviewAt(userId),
                kanjiReviewService.getNextStudyAt(userId)
        );
    }

    @PostMapping("/{userId}/answer")
    @PreAuthorize("@securityService.matchesUserId(#userId, authentication.principal)")
    public Map<String, Boolean> answerKanji(@PathVariable("userId") Long userId,
                                            @RequestBody KanjiAnswer answer) {
        return Map.of("isCorrect", kanjiAnswerService.checkAnswer(answer));
    }
}
