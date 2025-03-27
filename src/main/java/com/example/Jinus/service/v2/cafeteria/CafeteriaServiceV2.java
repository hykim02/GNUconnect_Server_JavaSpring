package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeteriaServiceV2 {

    private final CampusServiceV2 campusServiceV2;
    private final CampusResponseServiceV2 campusResponseServiceV2;
    private final CacheServiceV2 cacheServiceV2;
    private final CafeteriaResponseServiceV2 cafeteriaResponseServiceV2;

    // 사용자가 선택한 블록 ID값에 따라 반환 조건 설정
    // campusId가 -1이면 사용자 정보가 존재하지 않는 경우임
    public String campusOrCafeteria(int userCampusId, int sysCampusId) {
        if (sysCampusId == -1) { // 더보기 버튼 누른 경우 -> 캠퍼스 리스트 반환
            return campusResponseServiceV2.makeCampusListCard();
        }
        // 사용자가 캠퍼스를 선택한 경우
        int targetCampusId = (sysCampusId > 0) ? sysCampusId : userCampusId;
        return (targetCampusId != -1) // 사용자 정보 DB 존재 여부
                ? returnCafeteriaListCard(targetCampusId) // 존재 O -> 식당 정보 반환
                : campusResponseServiceV2.makeCampusListCard(); // 존재 X -> 캠퍼스 정보 반환
    }

    // 식당 리스트 반환
    private String returnCafeteriaListCard(int campusId) {
        String campusName = campusServiceV2.getUserCampusName(campusId);
        List<CafeteriaDto> cafeteriaList = cacheServiceV2.getCafeteriaList(campusId);
        return cafeteriaResponseServiceV2.createCafeteriaListCard(campusName, cafeteriaList);
    }
}