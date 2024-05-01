package com.naz_desu.sumato.api.student.ai_chat.dao;

import com.naz_desu.sumato.api.student.ai_chat.entity.ChatHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryDao extends JpaRepository<ChatHistory, Long> {

    @Query("SELECT ch FROM ChatHistory ch WHERE ch.user.id = :userId")
    List<ChatHistory> getLastMessages(long userId, Pageable pageable);
}
