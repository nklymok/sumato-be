package com.naz_desu.sumato.api.public_api;

import com.naz_desu.sumato.api.public_api.dto.KanjiDto;
import com.naz_desu.sumato.api.public_api.service.AddKanjiService;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UpdateUserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.service.SumatoUserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicApiController {
    private final SumatoUserProfileService userProfileService;
    private final AddKanjiService kanjiService;

    @GetMapping("/profile/{userId}")
    public UserProfileDTO getUserProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    @PostMapping("/kanji")
    public void postKanji(@RequestBody @Valid KanjiDto kanjiDto) {
        kanjiService.addKanji(kanjiDto);
    }

}
