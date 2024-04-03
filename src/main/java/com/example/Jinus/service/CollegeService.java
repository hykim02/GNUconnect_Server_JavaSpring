package com.example.Jinus.service;

import com.example.Jinus.entity.CollegeEntity;
import com.example.Jinus.repository.CollegeRepository;
import org.springframework.stereotype.Service;

@Service
public class CollegeService {
    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public String getCollegeName(int collegeId) {
        CollegeEntity collegeEntity = collegeRepository.findById(collegeId).orElse(null);

        if (collegeEntity != null) {
            return collegeEntity.getCollegeEng(); // 학과 영어 이름 리턴
        } else {
            return null;
        }
    }
}
