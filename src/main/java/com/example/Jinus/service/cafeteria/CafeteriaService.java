package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.service.userInfo.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeteriaService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CafeteriaRepository cafeteriaRepository;

    @Autowired
    public CafeteriaService(CafeteriaRepository cafeteriaRepository) {
        this.cafeteriaRepository = cafeteriaRepository;
    }

    // 캠퍼스 id로 식당 리스트 찾기
    public List<Object[]> getCafeteriaList(int campusId) {
        return cafeteriaRepository.findCafeteriaListByCampusId(campusId);
    }

    // cafeteriaName으로 cafeteria_id 찾기
    public int getCafeteriaIdByName(String sysCafeteriaName) {
        // cafeteriaName을 사용해 CafeteriaRepository에서 CafeteriaEntity 조회
        int cafeteriaId = cafeteriaRepository.findIdByName(sysCafeteriaName);

        return cafeteriaId;
    }

    // campusId와 cafeteriaName으로 cafeteria_id 찾기(식당이름 중복된 경우)
    public int getCafeteriaIdByCampusId(String sysCafeteriaName, int campusId) {
        Integer cafeteriaId = cafeteriaRepository.findIdByNameAndCampusId(sysCafeteriaName, campusId);

        if (cafeteriaId != null) {
            return cafeteriaId;
        } else {
            return 0;
        }
    }

    // 식당 이름으로 캠퍼스 id 찾기 (학과 인증하지 않은 경우)
    public int getCampusIdByName(String cafeteriaName) {
        int campusId = cafeteriaRepository.findCampusId(cafeteriaName);

        return campusId;
    }

    // 식당 id로 썸네일 url 찾기
    public String getCampusThumnail(int cafeteriaId) {
        String thumnailUrl = cafeteriaRepository.findThumnailUrl(cafeteriaId);

        return thumnailUrl;
    }
}
