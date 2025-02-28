package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.response.ButtonDto;
import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CafeteriaServiceV2 {

    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;
    private final CampusServiceV2 campusServiceV2;

    public CafeteriaServiceV2(
            CafeteriaRepositoryV2 cafeteriaRepositoryV2,
            CampusServiceV2 campusServiceV2) {
        this.cafeteriaRepositoryV2 = cafeteriaRepositoryV2;
        this.campusServiceV2 = campusServiceV2;
    }

    // 사용자의 campusId와 동일한 식당리스트 찾기
    public List<Object[]> getCafeteriaList(int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaListByCampusId(campusId);
    }

    // 식당 리스트 반환 메소드
    public String makeCafeteriaListCard(int campusId) {
        String campusName = campusServiceV2.getUserCampusName(campusId);
        List<Object[]> cafeteriaList = getCafeteriaList(campusId);

        // 식당 리스트 객체 생성
        List<ListItemDto> listItems = mappingCafeteriaList(campusName, cafeteriaList);
        // response 객체 생성
        ResponseDto responseDto = ListCardResponse.mappingResponseDto("어떤 교내 식당 정보가 알고싶어 ?", listItems, mappingButtonDto());

        return JsonUtils.toJsonResponse(responseDto);
    }

    // 식당 리스트 객체 생성
    public List<ListItemDto> mappingCafeteriaList(String campusName, List<Object[]> cafeteriaList) {
        List<ListItemDto> listItems = new ArrayList<>();
        for (Object[]  cafeteria : cafeteriaList) {
            String cafeteriaName = cafeteria[0].toString();
            String imageUrl = cafeteria[1].toString();
            String userMessage = campusName + " " + cafeteriaName;

            // 리스트 아이템 객체 생성
            ListItemDto listItem = new ListItemDto(cafeteriaName, campusName, imageUrl, "message", userMessage);
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
}
