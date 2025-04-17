package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeteriaQueryServiceV2 {
    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;

//    @Cacheable(value = "cafeteriaId", key = "#campusId + '::' + #cafeteriaName",
//            unless = "#result == -1",
//            cacheManager = "contentCacheManager")
    public int getCafeteriaId(String cafeteriaName, int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaId(cafeteriaName, campusId).orElse(-1);
    }

    public String getImgUrl(int cafeteriaId) {
        return cafeteriaRepositoryV2.findImgUrlByCafeteriaId(cafeteriaId);
    }
}
