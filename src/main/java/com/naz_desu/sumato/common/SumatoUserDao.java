package com.naz_desu.sumato.common;

import com.naz_desu.sumato.common.entity.SumatoUser;
import com.naz_desu.sumato.api.student.stats.GlobalStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SumatoUserDao extends JpaRepository<SumatoUser, Long> {

    @Query("SELECT u.id FROM SumatoUser u")
    List<Long> findAllIds();

    @Query("SELECT COUNT(u.id) > 0 FROM SumatoUser u WHERE u.id = :userId AND u.authId = :oauthUserId")
    boolean userIdMatchesOauthUserId(@Param("userId") Long userId, @Param("oauthUserId") String oauthUserId);

    @Query("select u.id from SumatoUser u where u.publicId = :studentId")
    Long getIdByPublicId(String studentId);

    @Query("select u.id from SumatoUser u where u.authId = :authId")
    long getIdByAuthId(String authId);

    /**
     * Fetch global stats: for each user, their id, name, and count of new kanji unlocks between two instants.
     */
    @Query(
        "SELECT new com.naz_desu.sumato.api.student.stats.GlobalStat(u.id, up.name, COUNT(cr)) " +
        "FROM SumatoUser u " +
        "JOIN UserProfile up ON up.user = u " +
        "LEFT JOIN KanjiReview cr ON cr.user = u AND cr.isFirstReview = TRUE AND cr.nextReviewAt BETWEEN :from AND :to " +
        "GROUP BY u.id, up.name " +
        "ORDER BY COUNT(cr) DESC"
    )
    List<GlobalStat> findGlobalStats(@Param("from") Instant from, @Param("to") Instant to);
}
