package com.naz_desu.sumato.api.student.ai_chat.entity;


import com.naz_desu.sumato.api.student.ai_chat.dto.ChatRole;
import com.naz_desu.sumato.common.entity.SumatoUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "chat_history")
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private SumatoUser user;
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatRole role;

    @CreatedDate
    private Instant sentAt;

}
