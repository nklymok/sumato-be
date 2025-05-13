package com.naz_desu.sumato.api.student.stats;

/**
 * DTO representing global statistics for users, containing user id, name, and number of kanji studied.
 */
public record GlobalStat(Long id, String name, Long kanjisStudied) {
}
