package com.naz_desu.sumato.api.student.kanji.dao;

import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserKanjiDao extends JpaRepository<KanjiReview, Long> {

    // === INITIAL / NEW LESSON COUNTS ===

    /** How many brand‐new (first‐review) kanji the user still has queued. */
    @Query("""
      SELECT COUNT(cr)
      FROM KanjiReview cr
      WHERE cr.user.id = :userId
        AND cr.isFirstReview = TRUE
        AND cr.nextReviewAt <= :currentTime
      """)
    int getKanjiToStudyCount(@Param("userId") Long userId, @Param("currentTime") Instant currentTime);

    /** How many ‘review‐again’ kanji are due at or before now. */
    @Query("""
      SELECT COUNT(cr)
      FROM KanjiReview cr
      WHERE cr.user.id = :userId
        AND cr.isFirstReview = FALSE
        AND cr.nextReviewAt <= :currentTime
      """)
    int getKanjiToReviewCount(
            @Param("userId") Long userId,
            @Param("currentTime") Instant currentTime
    );

    // === FETCH THE ACTUAL REVIEWS ===

    /** Get the full KanjiReview entities for first‐time reviews (with examples). */
    @Query("""
      SELECT cr
      FROM KanjiReview cr
      JOIN FETCH cr.kanji k
      LEFT JOIN FETCH k.examples ex
      WHERE cr.user.id = :userId
        AND cr.isFirstReview = TRUE
        AND cr.nextReviewAt <= :currentTime
      """)
    List<KanjiReview> getKanjiReviewsToStudy(@Param("userId") Long userId, @Param("currentTime") Instant currentTime);

    /** Get the full KanjiReview entities for due repeat‐reviews (with examples). */
    @Query("""
      SELECT cr
      FROM KanjiReview cr
      JOIN FETCH cr.kanji k
      LEFT JOIN FETCH k.examples ex
      WHERE cr.user.id = :userId
        AND cr.isFirstReview = FALSE
        AND cr.nextReviewAt <= :currentTime
      """)
    List<KanjiReview> getKanjiReviews(
            @Param("userId") Long userId,
            @Param("currentTime") Instant currentTime
    );

    // === ASSIGN NEW LESSONS ===

    @Modifying
    @Query(value =
            """
            INSERT INTO user_kanji_review
              (user_id, kanji_id, next_review_at, is_first_review, easiness, interval, repetitions)
            SELECT :userId, k.id, :reviewAt, TRUE, 2.5, 1, 0
            FROM kanji k
            LEFT JOIN user_kanji_review ur
              ON ur.kanji_id = k.id AND ur.user_id = :userId
            WHERE ur.id IS NULL
            ORDER BY k.id   -- or whatever your curriculum order is
            LIMIT :amount
            """,
            nativeQuery = true)
    void assignNewKanjiToUser(
            @Param("userId") Long userId,
            @Param("amount") int amount,
            @Param("reviewAt") Instant reviewAt
    );

    // === LEARNED / MASTERED COUNTS ===

    /** Total kanji the user has reviewed at least once. */
    @Query("""
      SELECT COUNT(cr)
      FROM KanjiReview cr
      WHERE cr.user.id = :userId
        AND cr.repetitions > 0
      """)
    int getLearnedKanjiCount(@Param("userId") Long userId);

    /** Count of new‐lesson unlocks between two instants (used for daily cap). */
    @Query("""
      SELECT COUNT(cr)
      FROM KanjiReview cr
      WHERE cr.user.id = :userId
        AND cr.isFirstReview = TRUE
        AND cr.nextReviewAt BETWEEN :from AND :to
      """)
    int countUnlocks(
            @Param("userId") Long userId,
            @Param("from")   Instant from,
            @Param("to")     Instant to
    );

    /**
     * How many items the user has *mastered* since a given instant:
     * i.e. their repetitions field has reached the given threshold.
     */
    @Query("""
      SELECT COUNT(cr)
      FROM KanjiReview cr
      WHERE cr.user.id = :userId
        AND cr.reviewedAt > :since
        AND cr.repetitions >= :threshold
      """)
    int countMasteredSince(
            @Param("userId")    Long     userId,
            @Param("since")     Instant  since,
            @Param("threshold") int      threshold
    );

    // === REVIEW STATE UPDATE (SM-2 RESULT) ===

    @Modifying
    @Query("""
      UPDATE KanjiReview cr
      SET
        cr.nextReviewAt = :nextReviewAt,
        cr.reviewedAt   = :now,
        cr.easiness     = :easiness,
        cr.interval     = :interval,
        cr.repetitions  = :repetitions,
        cr.isFirstReview = FALSE
      WHERE cr.id = :reviewId
      """)
    void updateReviewDates(
            @Param("reviewId")    Long    reviewId,
            @Param("now")         Instant now,
            @Param("nextReviewAt") Instant nextReviewAt,
            @Param("easiness")    double  easiness,
            @Param("interval")    int     interval,
            @Param("repetitions") int     repetitions
    );

    // === OTHER HELPERS ===

    @Query("SELECT kr.kanji FROM KanjiReview kr WHERE kr.user.id = :userId")
    List<Kanji> getLastLearnedKanjis(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
      SELECT MIN(cr.nextReviewAt)
        FROM KanjiReview cr
       WHERE cr.user.id = :userId
         AND cr.isFirstReview = FALSE
         AND cr.nextReviewAt > :now
    """)
    Instant getNextReviewAt(@Param("userId") Long userId,
                            @Param("now") Instant now);

    @Query("""
      SELECT MIN(cr.nextReviewAt)
        FROM KanjiReview cr
       WHERE cr.user.id = :userId
         AND cr.isFirstReview = TRUE
         AND cr.nextReviewAt > :now
    """)
    Instant getNextStudyAt(@Param("userId") Long userId,
                           @Param("now") Instant now);

}
