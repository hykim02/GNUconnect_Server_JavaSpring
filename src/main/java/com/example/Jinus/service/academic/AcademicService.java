package com.example.Jinus.service.academic;

import com.example.Jinus.entity.academic.AcademicEntity;
import com.example.Jinus.repository.academic.AcademicRepository;
import com.example.Jinus.service.CollegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AcademicService {
    private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);
    private final AcademicRepository academicRepository;

    public AcademicService(AcademicRepository calendarRepository) {
        this.academicRepository = calendarRepository;
    }

    public List<HashMap<String, String>> getAcademicContents(int currentMonth) {
        List<AcademicEntity> entities = academicRepository.findCalendarEntities(currentMonth);
        List<HashMap<String, String>> academicList = new ArrayList<>();

        // 해당 월의 학사일정이 존재하지 않는 경우
        if (entities != null) {
            for (AcademicEntity entity : entities) {
                HashMap<String, String> academicMap = new HashMap<>();

                academicMap.put("type", String.valueOf(entity.getCalendarType()));
                academicMap.put("content", entity.getContent());
                academicMap.put("end_date", entity.getEndDate());
                academicMap.put("start_date", entity.getStartDate());
                academicList.add(academicMap);
            }
        } else {
            logger.debug("db에 해당 월의 학사일정이 존재하지 않음");
            academicList = null;
        }

        return academicList;
    }
}
