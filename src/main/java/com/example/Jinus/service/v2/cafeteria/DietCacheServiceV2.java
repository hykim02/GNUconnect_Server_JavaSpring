package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietCacheServiceV2 {
    private final DietRepositoryV2 dietRepositoryV2;

    @Cacheable(
            value = "dietList",
            key = "#parameters.dietDate + '::' + #parameters.period + '::' + #cafeteriaId",
            unless = "#result == null || #result.isEmpty()",
            cacheManager = "contentCacheManager"
    )
    // 식단 데이터 가져오기
    public List<DietDto> getDietList(HandleRequestDto parameters, int cafeteriaId) {
        System.out.println("getDietList 호출");
        // 오늘, 내일 문자열로 날짜 설정하기
        Date dietDate = parameters.getDietDate();
        // 식단 데이터 반환
        return dietRepositoryV2.findDietList(dietDate, parameters.getPeriod(), cafeteriaId);
    }


}
