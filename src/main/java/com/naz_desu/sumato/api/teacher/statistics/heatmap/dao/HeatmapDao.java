package com.naz_desu.sumato.api.teacher.statistics.heatmap.dao;

import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;
import com.naz_desu.sumato.api.teacher.statistics.heatmap.dto.HeatMapSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HeatmapDao extends JpaRepository<KanjiReview, Long> {

    @Query(value = """
        SELECT
            dt.date AS date,
            COALESCE(completed.count, 0) AS reviewsCompleted,
            COALESCE(future.count, 0) AS reviewsToDo
        FROM
            (
                SELECT DATE(reviewed_at) AS date
                FROM sumato_user_kanji_review
                         JOIN sumato_user su ON sumato_user_kanji_review.user_id = su.id
                WHERE su.id = :userId AND reviewed_at >= :fromDate
                GROUP BY DATE(reviewed_at)
                UNION
                SELECT DATE(next_review_at) AS date
                FROM sumato_user_kanji_review
                         JOIN sumato_user su ON sumato_user_kanji_review.user_id = su.id
                WHERE su.id = :userId AND next_review_at >= :fromDate
                GROUP BY DATE(next_review_at)
            ) AS dt
                LEFT JOIN
            (
                SELECT DATE(reviewed_at) AS date, COUNT(*) AS count
                FROM sumato_user_kanji_review
                         JOIN sumato_user su ON sumato_user_kanji_review.user_id = su.id
                WHERE su.id = :userId AND reviewed_at >= :fromDate
                GROUP BY DATE(reviewed_at)
            ) AS completed ON dt.date = completed.date
                LEFT JOIN
            (
                SELECT DATE(next_review_at) AS date, COUNT(*) AS count
                FROM sumato_user_kanji_review
                         JOIN sumato_user su ON sumato_user_kanji_review.user_id = su.id
                WHERE su.id = :userId AND next_review_at >= :fromDate
                GROUP BY DATE(next_review_at)
            ) AS future ON dt.date = future.date
        ORDER BY dt.date;
""", nativeQuery = true)
    List<HeatMapSpot> findHeatMapSpotsByUserId(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate);

}
