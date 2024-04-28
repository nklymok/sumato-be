package com.naz_desu.sumato.api.teacher.statistics.heatmap.dto;

import java.time.LocalDate;

public interface HeatMapSpot {
    int getReviewsCompleted();
    int getReviewsToDo();
    LocalDate getDate();
}

