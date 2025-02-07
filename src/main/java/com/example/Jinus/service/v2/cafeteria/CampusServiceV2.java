package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusServiceV2 {
    private final CampusRepositoryV2 campusRepositoryV2;

    @Autowired
    public CampusServiceV2(CampusRepositoryV2 campusRepositoryV2) {
        this.campusRepositoryV2 = campusRepositoryV2;
    }

    // 사용자의 campusId 받아 캠퍼스 이름 찾기
    public String findUserCampusName(int campusId) {
        return campusRepositoryV2.findCampusNameById(campusId);
    }

    // id < 5인 캠퍼스 찾아 리스트로 반환
    public List<CampusEntity> findCampusList() {
        return campusRepositoryV2.findCampusList();
    }
}
