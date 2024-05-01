package com.naz_desu.sumato.api.student.ai_chat.dto;

import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record AiChatMessageDTO(String id, String text, String role) implements Message {

    public AiChatMessageDTO() {
        this(UUID.randomUUID().toString(), "", "");
    }

    public AiChatMessageDTO(String text, String role) {
        this(UUID.randomUUID().toString(), text, role);
    }

    @Override
    public String getContent() {
        return text;
    }

    @Override
    public List<Media> getMedia() {
        return null;
    }

    @Override
    public Map<String, Object> getProperties() {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return role.equals("user") ? MessageType.USER : MessageType.ASSISTANT;
    }
}
