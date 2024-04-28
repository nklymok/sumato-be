package com.naz_desu.sumato.api.teacher.statistics.heatmap.service;

import com.naz_desu.sumato.api.teacher.statistics.heatmap.dao.HeatmapDao;
import com.naz_desu.sumato.api.teacher.statistics.heatmap.dto.HeatMapSpot;
import com.naz_desu.sumato.api.teacher.statistics.heatmap.dto.ReviewHeatmapDTO;
import com.naz_desu.sumato.common.SumatoUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HeatmapService {

    private final HeatmapDao heatmapDao;
    private final SumatoUserDao userDao;

    public ReviewHeatmapDTO getReviewHeatmap(String studentId) {
        Long userId = userDao.getIdByPublicId(studentId);
        LocalDate fromDate = LocalDate.of(LocalDateTime.now().getYear(), 1, 1);
        List<HeatMapSpot> spots = heatmapDao.findHeatMapSpotsByUserId(userId, fromDate);
        long daysLearned = spots.stream().filter(spot -> spot.getReviewsCompleted() != 0)
                .map(HeatMapSpot::getDate)
                .distinct()
                .count();
        long longestStreakDays = getLongestStreakDays(spots);
        long currentStreak = getCurrentStreak(spots);
        long maxReviews = spots.stream().mapToLong(HeatMapSpot::getReviewsCompleted).max().orElse(0);
        return new ReviewHeatmapDTO(daysLearned, (int) longestStreakDays, (int) currentStreak, maxReviews, spots);
    }

    private long getCurrentStreak(List<HeatMapSpot> spots) {
        int indexYesterdaySpot = spots.stream().filter(spot -> spot.getDate().equals(LocalDate.now()
                        .minusDays(1)))
                .findFirst()
                .map(spots::indexOf)
                .orElse(-1);
        if (indexYesterdaySpot == -1) {
            return 0;
        }
        if (spots.get(indexYesterdaySpot).getReviewsCompleted() == 0) {
            return 0;
        }

        long streak = 1;
        for (int i = indexYesterdaySpot - 1; i >= 0; i--) {
            if (spots.get(i).getReviewsCompleted() == 0) {
                break;
            }
            if (spots.get(i).getDate().isEqual(spots.get(i + 1).getDate().minusDays(1))) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    private long getLongestStreakDays(List<HeatMapSpot> spots) {
        int longestStreak = 0;
        int currentStreak = 0;
        LocalDate lastDate = null;
        for (HeatMapSpot spot : spots) {
            if (spot.getReviewsCompleted() > 0) {
                if (lastDate == null || spot.getDate().isEqual(lastDate.plusDays(1))) {
                    currentStreak++;
                } else {
                    longestStreak = Math.max(longestStreak, currentStreak);
                    currentStreak = 1;
                }
                lastDate = spot.getDate();
            }
        }
        return Math.max(longestStreak, currentStreak);
    }

}
