package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CafeteriaService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CafeteriaRepository cafeteriaRepository;

    @Autowired
    public CafeteriaService(CafeteriaRepository cafeteriaRepository) {
        this.cafeteriaRepository = cafeteriaRepository;
        logger.info("CafeteriaService 실행");
    }

    // cafeteriaName으로 cafeteria_id 찾기
    public int getCafeteriaIdByName(String sysCafeteriaName) {
        logger.info("getCafeteriaIdByName 실행");

        // cafeteriaName을 사용해 CafeteriaRepository에서 CafeteriaEntity 조회
        int cafeteriaId = cafeteriaRepository.findIdByName(sysCafeteriaName);
        logger.info("cafeteriaId : {}", cafeteriaId);

        return cafeteriaId;
    }

    // campusId와 cafeteriaName으로 cafeteria_id 찾기(식당이름 중복된 경우)
    public int getCafeteriaIdByCampusId(String sysCafeteriaName, int campusId) {
        logger.info("getCafeteriaIdByCampusId 실행");

        Integer cafeteriaId = cafeteriaRepository.findIdByNameAndCampusId(sysCafeteriaName, campusId);
        logger.info("cafeteriaId : {}", cafeteriaId);

        if (cafeteriaId != null) {
            return cafeteriaId;
        } else {
            return 0;
        }
    }

    // 식당 이름으로 캠퍼스 id 찾기 (학과 인증하지 않은 경우)
    public int getCampusIdByName(String cafeteriaName) {
        logger.info("getCampusIdByName 실행");

        int campusId = cafeteriaRepository.findIdByName(cafeteriaName);
        logger.info("campusId : {}", campusId);

        return campusId;
    }
}
