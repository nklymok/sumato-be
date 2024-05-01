package com.naz_desu.sumato.api.student.ai_chat;

import com.naz_desu.sumato.api.student.ai_chat.dto.AiChatMessageDTO;
import com.naz_desu.sumato.api.student.ai_chat.service.AiChatService;
import com.naz_desu.sumato.common.config.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/ai-chat")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    @PostMapping
    public AiChatMessageDTO getAiChatMessage(@RequestBody AiChatMessageDTO message, UserIdDto userId) {
        return aiChatService.respond(message, userId);
    }

    @GetMapping("/history")
    public List<AiChatMessageDTO> getAiChatMessages(UserIdDto userId) {
        return aiChatService.getChatHistory(userId);
    }


}
