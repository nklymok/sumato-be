package com.naz_desu.sumato.auth.service;

import com.naz_desu.sumato.common.SumatoUserDao;
import com.naz_desu.sumato.auth.model.PostSignUpRequest;
import com.naz_desu.sumato.entity.SumatoUser;
import com.naz_desu.sumato.srs.NewKanjiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.naz_desu.sumato.constants.SumatoConstants.MAX_NEW_KANJI_DAILY;

@Service
@RequiredArgsConstructor
public class CreateUserService {
    private final SumatoUserDao sumatoUserDao;
    private final NewKanjiService newKanjiService;

    @Transactional
    public Long createUser(PostSignUpRequest request) {
        SumatoUser user = buildUserFromRequest(request);
        user = sumatoUserDao.save(user);
        newKanjiService.assignNewKanji(user.getId(), MAX_NEW_KANJI_DAILY);
        return user.getId();
    }

    private SumatoUser buildUserFromRequest(PostSignUpRequest request) {
        var geoData = request.request().geoip();
        var userData = request.user();
        SumatoUser user = new SumatoUser();
        user.setAuthId(userData.userId());
        user.setPublicId(UUID.randomUUID().toString().replace("-", ""));
        user.setRegisteredAt(userData.createdAt());
        user.setIpAddress(request.request().ip());
        user.setIpAddressCountry(geoData.countryCode());
        return user;
    }

}
