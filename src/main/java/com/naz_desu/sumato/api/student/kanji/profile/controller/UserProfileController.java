package com.naz_desu.sumato.api.student.kanji.profile.controller;

import com.naz_desu.sumato.api.student.kanji.profile.dto.UpdateUserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.api.student.kanji.profile.service.SumatoUserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final SumatoUserProfileService userProfileService;


    @GetMapping("/{userId}")
    public UserProfileDTO getUserProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("@securityService.matchesUserId(#userId, authentication.principal)")
    public void updateUserProfile(@PathVariable Long userId, @RequestBody @Valid UpdateUserProfileDTO updateDto) {
        userProfileService.updateUserProfile(userId, updateDto);
    }

}
