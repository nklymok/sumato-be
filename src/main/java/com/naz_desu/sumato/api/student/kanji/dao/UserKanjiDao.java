package com.naz_desu.sumato.api.student.kanji.dao;

import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiStats;
import com.naz_desu.sumato.api.student.kanji.entity.KanjiReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserKanjiDao extends JpaRepository<KanjiReview, Long> {

    @Query("Select count(k) from KanjiReview k " +
            "where k.user.id = :userId " +
            " and k.isFirstReview = true " +
            "AND k.id IN " +
            "(SELECT MAX(kr.id) FROM KanjiReview kr GROUP BY kr.kanji.id)")
    int getKanjiToStudyCount(@Param("userId") Long userId);


    @Query("select count(k) from KanjiReview k " +
            "where k.user.id = :userId " +
            "and k.nextReviewAt <= :currentTime " +
            "and k.isFirstReview = false " +
            "and k.id IN " +
            "(SELECT MAX(kr.id) FROM KanjiReview kr GROUP BY kr.kanji.id)")
    int getKanjiToReviewCount(@Param("userId") Long userId, @Param("currentTime") Instant currentTime);

    @Query("SELECT kr " +
            "FROM KanjiReview kr " +
            "JOIN FETCH kr.kanji ka " +
            "JOIN FETCH ka.examples ke " +
            "WHERE kr.id IN " +
            "(SELECT MAX(kr.id) FROM KanjiReview kr GROUP BY kr.kanji.id) " +
            "AND kr.id IN " +
            "(SELECT kr.id FROM KanjiReview kr WHERE kr.user.id = :userId AND kr.isFirstReview = true)")
    List<KanjiReview> getKanjiReviewsToStudy(@Param("userId") Long userId);

    @Query("SELECT k " +
            "FROM KanjiReview k " +
            "JOIN FETCH k.kanji ka " +
            "JOIN FETCH ka.examples ke " +
            "WHERE k.nextReviewAt <= :currentTime " +
            "AND k.user.id = :userId AND k.isFirstReview = false " +
            "AND k.id IN " +
            "(SELECT MAX(kr.id) FROM KanjiReview kr GROUP BY kr.kanji.id)")
    List<KanjiReview> getKanjiReviews(@Param("userId") Long userId, @Param("currentTime") Instant currentTime);

    @Modifying
    @Query(value = "INSERT INTO sumato_user_kanji_review (user_id, kanji_id, next_review_at, is_first_review) " +
            "SELECT :userId, k.id, :reviewAt, true " +
            "FROM sumato_kanji k " +
            "LEFT JOIN sumato_user_kanji_review kr ON k.id = kr.kanji_id AND kr.user_id = :userId " +
            "WHERE kr.id IS NULL " +
            "ORDER BY k.id " +
            "LIMIT :amount", nativeQuery = true)
    void assignNewKanjiToUser(@Param("userId") Long userId, @Param("amount") int amount, @Param("reviewAt") Instant reviewAt);

    @Query("SELECT count(k) " +
            "FROM KanjiReview k " +
            "WHERE k.user.id = :userId " +
            "AND k.isFirstReview = false " +
            "AND k.id IN " +
            "(SELECT MAX(kr.id) FROM KanjiReview kr GROUP BY kr.kanji.id)")
    Integer getLearnedKanjiCount(Long userId);

    @Query("SELECT k.kanji " +
            "FROM KanjiReview k " +
            "WHERE k.user.id = :userId " +
            "AND k.id = :reviewId ")
    Kanji getKanjiFromReview(Long userId, Long reviewId);

    @Query("SELECT k " +
            "FROM KanjiStats k " +
            "WHERE k.kanjiReview.user.id = :userId " +
            "AND k.kanjiReview.kanji.id = :kanjiId " +
            "AND k.id IN " +
            "(SELECT MAX(ks.id) FROM KanjiStats ks GROUP BY ks.kanjiReview.id)")
    Optional<KanjiStats> findLatestKanjiStats(Long userId, Long kanjiId);

    @Modifying
    @Query("UPDATE KanjiReview k " +
            "SET k.nextReviewAt = :nextReviewAt, " +
            "k.reviewedAt = :now " +
            "WHERE k.id = :reviewId")
    void updateReviewDates(Long reviewId, Instant now, Instant nextReviewAt);
}
