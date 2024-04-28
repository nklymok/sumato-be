package com.naz_desu.sumato.api.teacher.statistics.controller;

import com.naz_desu.sumato.api.teacher.statistics.heatmap.dto.ReviewHeatmapDTO;
import com.naz_desu.sumato.api.teacher.statistics.heatmap.service.HeatmapService;
import com.naz_desu.sumato.api.teacher.statistics.piechart.PiechartService;
import com.naz_desu.sumato.api.teacher.statistics.piechart.dto.KanjiPiechartDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tutor/statistics/{studentId}")
@RequiredArgsConstructor
public class TutorStatisticsController {
    private final HeatmapService heatmapService;
    private final PiechartService piechartService;

    @GetMapping("/heatmap")
    public ReviewHeatmapDTO getReviewHeatmap(@PathVariable @Valid @NotBlank String studentId) {
        return heatmapService.getReviewHeatmap(studentId);
    }

    @GetMapping("/piechart")
    public KanjiPiechartDTO getKanjiPiechart(@PathVariable @Valid @NotBlank String studentId) {
        return piechartService.getPiechart(studentId);
    }

}
