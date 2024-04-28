package com.naz_desu.sumato.api.teacher.statistics.piechart;

import com.naz_desu.sumato.api.teacher.statistics.piechart.dao.PiechartDao;
import com.naz_desu.sumato.api.teacher.statistics.piechart.dto.KanjiPiechartDTO;
import com.naz_desu.sumato.api.teacher.statistics.piechart.dto.PieChartSegment;
import com.naz_desu.sumato.common.SumatoUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PiechartService {
    private final SumatoUserDao userDao;
    private final PiechartDao piechartDao;
    public KanjiPiechartDTO getPiechart(String studentId) {
        Long userId = userDao.getIdByPublicId(studentId);
        List<PieChartSegment> segments = piechartDao.getPiechartSegmentsForUserId(userId);
        return new KanjiPiechartDTO(segments);
    }
}
