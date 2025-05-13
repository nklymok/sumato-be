package com.naz_desu.sumato.api.student.stats;

import com.naz_desu.sumato.api.student.stats.GlobalStat;
import com.naz_desu.sumato.common.SumatoUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service to fetch global statistics over all users.
 */
@Service
@RequiredArgsConstructor
public class GlobalStatsService {
    private final SumatoUserDao sumatoUserDao;

    /**
     * Fetches the list of global stats for all users between the given time range.
     *
     * @param from inclusive start Instant
     * @param to   exclusive end Instant
     * @return list of GlobalStat DTOs
     */
    public List<GlobalStat> getStats(Instant from, Instant to) {
        return sumatoUserDao.findGlobalStats(from, to);
    }
}