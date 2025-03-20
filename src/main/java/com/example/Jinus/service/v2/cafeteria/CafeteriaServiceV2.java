package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.dto.response.ButtonDto;
import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CafeteriaServiceV2 {

    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;
    private final CampusServiceV2 campusServiceV2;
    private final CacheServiceV2 cacheServiceV2;


    // 반환 조건 설정
    public String campusOrCafeteria(int campusId, int sysCampusId) {
        // 더보기 버튼 누른 경우
        if (sysCampusId == -1) {
            return campusServiceV2.makeCampusListCard();
        }

        // 사용자가 원하는 캠퍼스가 있을 때
        if (sysCampusId > 0) {
            return makeCafeteriaListCard(sysCampusId);
        }

        // 사용자가 원하는 캠퍼스가 없을 때
        return (campusId != -1) // 사용자 존재 여부
                ? makeCafeteriaListCard(campusId)
                : campusServiceV2.makeCampusListCard();
    }

    // 식당 리스트 반환 메소드
    public String makeCafeteriaListCard(int campusId) {
        String campusName = campusServiceV2.getUserCampusName(campusId);
        List<CafeteriaDto> cafeteriaList = cacheServiceV2.getCafeteriaList(campusId);

        // 식당 리스트 객체 생성
        List<ListItemDto> listItems = mappingCafeteriaList(campusName, cafeteriaList);
        // response 객체 생성
        ResponseDto responseDto = ListCardResponse.mappingResponseDto("어떤 교내 식당 정보가 알고싶어 ?", listItems, mappingButtonDto());

        return JsonUtils.toJsonResponse(responseDto);
    }


    // 식당 리스트 객체 생성
    public List<ListItemDto> mappingCafeteriaList(String campusName, List<CafeteriaDto> cafeteriaList) {
        List<ListItemDto> listItems = new ArrayList<>();
        for (CafeteriaDto  cafeteria : cafeteriaList) {
            String userMessage = campusName + " " + cafeteria.getCafeteriaNameKo();

            // 리스트 아이템 객체 생성
            ListItemDto listItem = new ListItemDto(cafeteria.getCafeteriaNameKo(), campusName,
                    cafeteria.getThumbnailUrl(), "message", userMessage);
            listItems.add(listItem);
        }
        return listItems;
    }


    // 더보기 버튼 리스트 생성
    public List<ButtonDto> mappingButtonDto() {
        List<ButtonDto> buttonDto = new ArrayList<>();
        Map<String, Object> extra = new HashMap<>();
        extra.put("sys_campus_id", -1);
        // 버튼 객체 생성
        ButtonDto button = new ButtonDto("더보기", "block", "66067167cdd882158c759fc2", extra);
        buttonDto.add(button);
        return buttonDto;
    }


//    @Cacheable(value = "cafeteriaId", key = "#cafeteriaName", // key 값을 'campusId + cafeteriaName' 조합으로 수정할 필요가 있음.
//            unless = "#result == -1",
//            cacheManager = "contentCacheManager")
    // 캠퍼스에 식당이 존재한다면 cafeteriaId 찾기
    public int getCafeteriaId(String cafeteriaName, int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaId(cafeteriaName, campusId).orElse(-1);
    }


    // 식당 imgUrl 찾기
    public String getImgUrl(int cafeteriaId) {
        return cafeteriaRepositoryV2.findImgUrlByCafeteriaId(cafeteriaId);
    }
}
