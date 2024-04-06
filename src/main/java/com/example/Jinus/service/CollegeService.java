package com.example.Jinus.service;

import com.example.Jinus.entity.CollegeEntity;
import com.example.Jinus.repository.CollegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CollegeService {
    private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);
    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
        logger.info("CollegeService 실행");
    }

    public String getCollegeName(int collegeId) {
        logger.info("getCollegeName 실행");
        CollegeEntity collegeEntity = collegeRepository.findById(collegeId).orElse(null);
        logger.info("collegeEntity {}", collegeEntity);

        if (collegeEntity != null) {
            logger.info("collegeEng: {}", collegeEntity.getCollegeEng());
            return collegeEntity.getCollegeEng(); // 학과 영어 이름 리턴(테이블 찾기 위함)
        } else {
            logger.error("CollegeService: collge를 찾을 수 없습니다.");
            return null;
        }
    }
}
