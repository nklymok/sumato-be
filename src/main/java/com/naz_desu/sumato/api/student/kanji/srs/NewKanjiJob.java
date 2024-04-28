package com.naz_desu.sumato.api.student.kanji.srs;

import com.naz_desu.sumato.common.SumatoUserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewKanjiJob {
    private final NewKanjiService newKanjiService;
    private final SumatoUserDao sumatoUserDao;

    // Run once a day
    @Scheduled(fixedRate = 86_400_000)
    public void addNewKanji() {
        log.info("Job started: Adding new kanji to study");
        sumatoUserDao.findAllIds().forEach(newKanjiService::assignNewKanji);
    }

}
