package com.example.Jinus.service.diet;

import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.repository.cafeteria.DietRepository;
import com.example.Jinus.service.cafeteria.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class DietQueryService {

    private final CacheService cacheServiceV2;

    // 메뉴 존재 여부에 따른 반환값 처리 로직
    public String getDietResponse(HandleRequestDto parameters, int cafeteriaId) {
        // 메뉴 가져오기
        List<DietDto> dietDtos = cacheServiceV2.getDietList(parameters, cafeteriaId);
        // 메뉴 존재하는 경우
        if (!dietDtos.isEmpty()) {
            // 메뉴 찾기
            MultiValueMap<String, String> dietList = getDiets(dietDtos);
            return processDietList(dietList).toString();
        }
        return "\n메뉴가 존재하지 않습니다."; // 메뉴가 없는 경우
    }


    // 카테고리별 메뉴 리스트 생성하기
    private MultiValueMap<String, String> getDiets(List<DietDto> dietDtos) {
        MultiValueMap<String, String> dietList = new LinkedMultiValueMap<>(); // 중복 키 허용(값을 리스트로 반환)

        for (DietDto o : dietDtos) {
            String key = (o.getDishCategory() != null) ? o.getDishCategory()
                    : (o.getDishType() != null) ? o.getDishType()
                    : "메뉴";

            dietList.add(key, o.getDishName());
        }
        return dietList;
    }

    // 카테고리별 메뉴 문자열로 나열하기
    private StringBuilder processDietList(MultiValueMap<String, String> dietList) {
        // 키 추출
        Set<String> keys = new TreeSet<>(dietList.keySet()); // 순서 보장 - 오름차순
        StringBuilder description = new StringBuilder();

        for (String key : keys) {
            description.append("\n[").append(key).append("]").append("\n");
            dietList.get(key).forEach(diet -> description.append(diet).append("\n"));
        }
        return description;
    }
}
