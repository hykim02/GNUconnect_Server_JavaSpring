package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheServiceV2 {
    private final DietRepositoryV2 dietRepositoryV2;
    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;

    @Cacheable(
            value = "dietList",
            key = "#parameters.dietDate + '::' + #parameters.period + '::' + #cafeteriaId",
            unless = "#result == null || #result.isEmpty()",
            cacheManager = "contentCacheManager"
    )
    // 식단 데이터 가져오기
    public List<DietDto> getDietList(HandleRequestDto parameters, int cafeteriaId) {
        // 오늘, 내일 문자열로 날짜 설정하기
        Date dietDate = parameters.getDietDate();
        // 식단 데이터 반환
        return dietRepositoryV2.findDietList(dietDate, parameters.getPeriod(), cafeteriaId);
    }


    // Redis에서 조회 (없으면 DB에서 가져옴)
    @Cacheable(value = "cafeteriaList", key = "#campusId", cacheManager = "contentCacheManager")
    public List<CafeteriaDto> getCafeteriaList(int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaListByCampusId(campusId);
    }
}
