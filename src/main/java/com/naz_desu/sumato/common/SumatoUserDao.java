package com.naz_desu.sumato.common;

import com.naz_desu.sumato.entity.SumatoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SumatoUserDao extends JpaRepository<SumatoUser, Long> {

    @Query("SELECT u.id FROM SumatoUser u")
    List<Long> findAllIds();

}