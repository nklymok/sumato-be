package com.naz_desu.sumato.api.teacher.statistics.heatmap.dto;

import java.util.List;

public record ReviewHeatmapDTO(long daysLearned, int longestStreakDays, int currentStreak, long maxReviewsCompleted, List<HeatMapSpot> heatmap) { }
