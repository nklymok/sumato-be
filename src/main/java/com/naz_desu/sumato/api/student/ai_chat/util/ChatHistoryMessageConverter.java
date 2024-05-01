package com.naz_desu.sumato.api.student.ai_chat.util;

import com.naz_desu.sumato.api.student.ai_chat.dto.AiChatMessageDTO;
import com.naz_desu.sumato.api.student.ai_chat.entity.ChatHistory;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ChatHistoryMessageConverter {

    public List<AiChatMessageDTO> convert(List<ChatHistory> chatHistoryList) {
        return chatHistoryList.stream()
                .map(ChatHistoryMessageConverter::convert)
                .collect(Collectors.toList());
    }

    private AiChatMessageDTO convert(ChatHistory chatHistory) {
        return new AiChatMessageDTO(chatHistory.getMessage(), chatHistory.getRole().getRole());
    }
}
