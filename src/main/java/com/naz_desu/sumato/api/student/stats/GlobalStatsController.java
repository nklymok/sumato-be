package com.naz_desu.sumato.api.student.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

/**
 * REST controller for fetching global weekly statistics.
 */
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class GlobalStatsController {

    private final GlobalStatsService globalStatsService;

    /**
     * GET /student/global-stats?from=YYYY-MM-DD&to=YYYY-MM-DD
     * Returns list of users with their id, name, and number of kanji studied in the given timeframe.
     *
     * @param from start date (inclusive) in ISO yyyy-MM-dd
     * @param to   end date (inclusive) in ISO yyyy-MM-dd
     * @return list of GlobalStat records
     */
    @GetMapping("/global-stats")
    public List<GlobalStat> getGlobalStats(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        Instant fromInstant = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        // toDate inclusive: move to next day start (exclusive)
        Instant toInstant = toDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return globalStatsService.getStats(fromInstant, toInstant);
    }
}
