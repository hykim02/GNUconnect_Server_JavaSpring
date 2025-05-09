package com.example.Jinus.service.userInfo;

import com.example.Jinus.entity.userInfo.CollegeEntity;
import com.example.Jinus.repository.userInfo.CollegeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CollegeService {
    private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);
    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public String getCollegeName(int collegeId) {
        CollegeEntity collegeEntity = collegeRepository.findById(collegeId).orElse(null);

        if (collegeEntity != null) {
            return collegeEntity.getCollegeEng(); // 학과 영어 이름 리턴(테이블 찾기 위함)
        } else {
            logger.debug("CollegeService: collge를 찾을 수 없습니다.");
            return null;
        }
    }

    public boolean checkEtcValue(int collegeId) {
        CollegeEntity collegeEntity = collegeRepository.findById(collegeId).orElse(null);
        return collegeEntity.isEtcValue();
    }

    // collegeId에 해당하는 campusId 찾기
    public int getCampusId(int collegeId) {
        int campusId = collegeRepository.findCampusId(collegeId);
        return campusId;
    }
}
