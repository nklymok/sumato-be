package com.naz_desu.sumato.api.student.ai_chat.service;

import com.naz_desu.sumato.api.student.ai_chat.dao.ChatHistoryDao;
import com.naz_desu.sumato.api.student.ai_chat.dto.AiChatMessageDTO;
import com.naz_desu.sumato.api.student.ai_chat.dto.ChatRole;
import com.naz_desu.sumato.api.student.ai_chat.entity.ChatHistory;
import com.naz_desu.sumato.api.student.ai_chat.util.ChatHistoryMessageConverter;
import com.naz_desu.sumato.common.SumatoUserDao;
import com.naz_desu.sumato.common.exception.SumatoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatHistoryDao chatHistoryDao;
    private final SumatoUserDao userDao;

    public List<AiChatMessageDTO> findLastMessages(long userId) {
        List<ChatHistory> lastMessages = chatHistoryDao.getLastMessages(userId, PageRequest.of(0, 10, Sort.by("sentAt").descending()));
        Collections.reverse(lastMessages);
        return ChatHistoryMessageConverter.convert(lastMessages);
    }

    public void saveMessage(long userId, AiChatMessageDTO message) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setMessage(message.text());
        chatHistory.setRole(ChatRole.from(message.role()));
        var user = userDao.findById(userId).orElseThrow(() -> new SumatoException("User with id {} not found", userId));
        chatHistory.setUser(user);
        chatHistoryDao.save(chatHistory);
    }
}
