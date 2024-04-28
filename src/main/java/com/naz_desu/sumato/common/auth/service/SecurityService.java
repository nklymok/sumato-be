package com.naz_desu.sumato.common.auth.service;

import com.naz_desu.sumato.common.SumatoUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final SumatoUserDao userDao;

    public boolean matchesUserId(Long userId, @AuthenticationPrincipal Jwt jwt) {
        String oauthUserId = jwt.getSubject();
        return userDao.userIdMatchesOauthUserId(userId, oauthUserId);
    }

}
