package com.naz_desu.sumato.api.student.ai_chat.service;

import com.naz_desu.sumato.api.student.ai_chat.dto.AiChatMessageDTO;
import com.naz_desu.sumato.api.student.ai_chat.util.ChatHistoryMessageConverter;
import com.naz_desu.sumato.api.student.kanji.dao.UserKanjiDao;
import com.naz_desu.sumato.api.student.kanji.entity.Kanji;
import com.naz_desu.sumato.common.SumatoUserDao;
import com.naz_desu.sumato.common.config.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final OpenAiChatClient openAiChatClient;
    private final ChatHistoryService chatHistoryService;
    private final SumatoUserDao userDao;
    private final UserKanjiDao userKanjiDao;


    public AiChatMessageDTO respond(AiChatMessageDTO message, UserIdDto userIdDto) {
        long userId = userDao.getIdByAuthId(userIdDto.authId());
        chatHistoryService.saveMessage(userId, message);
        Prompt prompt = buildPrompt(userId);
        AiChatMessageDTO response = new AiChatMessageDTO(openAiChatClient.call(prompt).getResult().getOutput().getContent(), "assistant");
        chatHistoryService.saveMessage(userId, response);
        return response;
    }

    private Prompt buildPrompt(long userId) {
        AiChatMessageDTO systemMessage = getSystemMessage(userId);
        List<AiChatMessageDTO> lastMessages = chatHistoryService.findLastMessages(userId);
        lastMessages.add(systemMessage);
        List<Message> messages = lastMessages.stream().map(msg -> (Message) msg).toList();
        return new Prompt(messages);
    }

    private AiChatMessageDTO getSystemMessage(long userId) {
        String kanjis = userKanjiDao.getLastLearnedKanjis(userId, PageRequest.of(0, 5, Sort.by("reviewedAt")
                .descending()))
                .stream()
                .map(Kanji::getValue)
                .collect(Collectors.joining(", "));
        if (Strings.isNotBlank(kanjis)) {
            kanjis = "By the way, they just learned these kanjis, so you can use these in conversation or even congratulate them about it: " + kanjis;
        }
        String systemMsg = Strings.join(List.of("You are Tanaka-san, a pen-pal for the user in an app called Sumato, a Japanese language learning app. The user may want to chat with you in English or Japanese, or their own language. If they text you in English, please respond in English, too, unless they ask you to switch language. If you chat in English, you can use some common Japanese words and explain them to user to make the chat more engaging. They most probably are beginners, so be nice. They may ask to explain kanjis or grammar. Keep your responses short, unless asked otherwise, and use text slang, maybe smiling faces like Japanese do on Line. Behave like a real chat friend.", kanjis), ' ');
        return new AiChatMessageDTO(systemMsg, "system");
    }

    public List<AiChatMessageDTO> getChatHistory(UserIdDto userIdDto) {
        long userId = userDao.getIdByAuthId(userIdDto.authId());
        return chatHistoryService.findLastMessages(userId);
    }
}
