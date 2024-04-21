package com.naz_desu.sumato.kanji.dao;

import com.naz_desu.sumato.kanji.entity.KanjiStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserKanjiStatsDao extends JpaRepository<KanjiStats, Long> {
}
