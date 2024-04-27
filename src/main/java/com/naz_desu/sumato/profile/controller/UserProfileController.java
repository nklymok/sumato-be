package com.naz_desu.sumato.profile.controller;

import com.naz_desu.sumato.profile.dto.UpdateUserProfileDTO;
import com.naz_desu.sumato.profile.dto.UserProfileDTO;
import com.naz_desu.sumato.profile.service.SumatoUserProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final SumatoUserProfileService userProfileService;


    @GetMapping("/{userId}")
    public UserProfileDTO getUserProfile(@PathVariable Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    @PutMapping("/{userId}")
    public void updateUserProfile(@PathVariable Long userId, @RequestBody @Valid UpdateUserProfileDTO updateDto) {
        userProfileService.updateUserProfile(userId, updateDto);
    }

}
