package com.naz_desu.sumato.api.public_api.dao;

import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanjiDao extends JpaRepository<Kanji, Long> {
}
