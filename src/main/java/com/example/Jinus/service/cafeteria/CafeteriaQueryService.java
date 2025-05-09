package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeteriaQueryService {
    private final CafeteriaRepository cafeteriaRepositoryV2;

//    @Cacheable(
//            value = "cafeteriaId",
//            key = "#p0 + '::' + #p1",
//            unless = "#result == -1",
//            cacheManager = "contentCacheManager")
    public int getCafeteriaId(String cafeteriaName, int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaId(cafeteriaName, campusId).orElse(-1);
    }

//    @Cacheable(
//            value = "cafeteriaUrl",
//            key = "#p0",
//            cacheManager = "contentCacheManager")
    public String getImgUrl(int cafeteriaId) {
        return cafeteriaRepositoryV2.findImgUrlByCafeteriaId(cafeteriaId);
    }
}
