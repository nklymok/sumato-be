package com.naz_desu.sumato.api.student.kanji.profile.dao;

import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.common.entity.userProfile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface SumatoUserProfileDao extends JpaRepository<UserProfile, Long> {

    @Query("SELECT " +
            "up.user.id as id," +
            "up.name as name," +
            "up.dangoCount as dangoCount," +
            "up.jlptLevel as level," +
            "up.user.publicId as publicId, " +
            "up.lastUnlockAt as lastUnlockAt " +
            "FROM UserProfile up WHERE up.user.id = :userId")
    Optional<UserProfileDTO> findDtoByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE UserProfile up SET up.lastUnlockAt = :lastUnlockAt WHERE up.user.id = :userId")
    void updateLastUnlockAt(@Param("userId") Long userId, @Param("lastUnlockAt") Instant lastUnlockAt);

    @Modifying
    @Query("UPDATE UserProfile up SET up.name = :name WHERE up.user.id = :userId")
    void updateName(Long userId, String name);
}
