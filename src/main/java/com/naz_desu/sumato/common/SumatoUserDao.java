package com.naz_desu.sumato.common;

import com.naz_desu.sumato.entity.SumatoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SumatoUserDao extends JpaRepository<SumatoUser, Long> {

    @Query("SELECT u.id FROM SumatoUser u")
    List<Long> findAllIds();

    @Query("SELECT COUNT(u.id) > 0 FROM SumatoUser u WHERE u.id = :userId AND u.authId = :oauthUserId")
    boolean userIdMatchesOauthUserId(@Param("userId") Long userId, @Param("oauthUserId") String oauthUserId);

    @Query("select u.id from SumatoUser u where u.publicId = :studentId")
    Long getIdByPublicId(String studentId);
}
