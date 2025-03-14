package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.request.HandleRequestDto;
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
            key = "#parameters.day + '::' + #parameters.period + '::' + #cafeteriaId",
            unless = "#result == null || #result.isEmpty()",
            cacheManager = "contentCacheManager"
    )
    // 식단 데이터 가져오기
    public List<DietDto> getDietList(HandleRequestDto parameters, int cafeteriaId) {
        System.out.println("getDietList 호출");
        // 오늘, 내일 문자열로 날짜 설정하기
        Date dietDate = getCurrentDate(parameters.getDay());
        // 식단 데이터 반환
        return dietRepositoryV2.findDietList(dietDate, parameters.getPeriod(), cafeteriaId);
    }

    // sysDay 파라미터 값으로 조회할 날짜 찾는 함수
    public Date getCurrentDate(String sysDate) {
        // 현재 날짜
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1); // 하루 뒤 날짜 계산

        if (sysDate.equals("오늘")) {
            return Date.valueOf(today);
        } else { // 내일
            return Date.valueOf(tomorrow);
        }
    }
}
