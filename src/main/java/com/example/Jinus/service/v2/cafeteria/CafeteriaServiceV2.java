package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeteriaServiceV2 {

    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;

    public CafeteriaServiceV2(CafeteriaRepositoryV2 cafeteriaRepositoryV2) {
        this.cafeteriaRepositoryV2 = cafeteriaRepositoryV2;
    }

    // 사용자의 campusId와 동일한 식당리스트 찾기
    public List<Object[]> findCafeteriaList(int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaListByCampusId(campusId);
    }
}
