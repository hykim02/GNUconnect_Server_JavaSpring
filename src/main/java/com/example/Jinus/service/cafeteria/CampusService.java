package com.example.Jinus.service.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.cafeteria.CampusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampusService {
    private final CampusRepository campusRepositoryV2;

    @Cacheable(
            value = "campusId",
            key = "#p0",
            cacheManager = "contentCacheManager")
    // 사용자의 campusId 받아 캠퍼스 이름 찾기
    public String getUserCampusName(int campusId) {
        return campusRepositoryV2.findCampusNameByCampusId(campusId);
    }

    // id < 5인 캠퍼스 찾아 리스트로 반환
    public List<CampusEntity> getCampusList() {
        return campusRepositoryV2.findCampusList();
    }

    @Cacheable(
            value = "campusName",
            key = "#p0",
            cacheManager = "contentCacheManager")
    // 캠퍼스 이름으로 id 찾기
    public int getCampusId(String campusName) {
        return campusRepositoryV2.findCampusIdByName(campusName);
    }
}
