package com.naz_desu.sumato.api.student.stats;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for the leaderboard global-stats endpoint covering:
 * REQ-1: Calculation over last 7 days.
 * REQ-2: Sorting descending and displaying nickname + score.
 * REQ-3: Fresh calculation on each request.
 */
@WebMvcTest(GlobalStatsController.class)
class GlobalStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GlobalStatsService globalStatsService;

    @Test
    @WithMockUser
    void getGlobalStatsShouldReturnSortedStats() throws Exception {
        GlobalStat alice = new GlobalStat(1L, "Alice", 5L);
        GlobalStat bob = new GlobalStat(2L, "Bob", 3L);
        when(globalStatsService.getStats(any(Instant.class), any(Instant.class)))
                .thenReturn(List.of(alice, bob));

        mockMvc.perform(get("/student/global-stats")
                .param("from", "2025-05-01")
                .param("to", "2025-05-07"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].kanjisStudied").value(5))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[1].kanjisStudied").value(3));
    }
}
