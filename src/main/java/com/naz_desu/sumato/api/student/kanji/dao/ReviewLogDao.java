package com.naz_desu.sumato.api.student.kanji.dao;

import com.naz_desu.sumato.api.student.kanji.entity.ReviewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLogDao extends JpaRepository<ReviewLog, Long> {
}
