package com.naz_desu.sumato.api.teacher.statistics.piechart.dao;

import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;
import com.naz_desu.sumato.api.teacher.statistics.piechart.dto.PieChartSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PiechartDao extends JpaRepository<KanjiReview, Long> {

    @Query(value = """
        SELECT
            label,
            value
        FROM
            (SELECT
                 'New' AS label,
                 (SELECT COUNT(*) FROM sumato_kanji) -
                 (SELECT SUM(value) FROM
                     (SELECT
                          CASE
                              WHEN EXTRACT(DAY FROM kr.next_review_at - kr.reviewed_at) >= 21 THEN 'Mature'
                              ELSE 'Learning'
                              END AS label,
                          COUNT(*) AS value
                      FROM sumato_user_kanji_review kr
                      WHERE kr.user_id = :userId
                      GROUP BY
                          CASE
                              WHEN EXTRACT(DAY FROM kr.next_review_at - kr.reviewed_at) >= 21 THEN 'Mature'
                              ELSE 'Learning'
                              END) AS subquery) AS value
             UNION
             (SELECT
                  'Learning' AS label,
                  SUM(CASE
                          WHEN EXTRACT(DAY FROM kr.next_review_at - kr.reviewed_at) < 21 THEN 1
                          ELSE 0
                      END) AS value
              FROM sumato_user_kanji_review kr
              WHERE kr.user_id = :userId)
             UNION
             (SELECT
                  'Mature' AS label,
                  SUM(CASE
                          WHEN EXTRACT(DAY FROM kr.next_review_at - kr.reviewed_at) >= 21 THEN 1
                          ELSE 0
                      END) AS value
              FROM sumato_user_kanji_review kr
              WHERE kr.user_id = :userId)
            ) AS result_set
        ORDER BY
            CASE label
                WHEN 'New' THEN 1
                WHEN 'Learning' THEN 2
                WHEN 'Mature' THEN 3
                END;
""", nativeQuery = true)
    List<PieChartSegment> getPiechartSegmentsForUserId(@Param("userId") Long userId);

}
