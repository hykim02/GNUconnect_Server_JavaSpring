package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CampusResponseServiceV2 {

    private final CampusServiceV2 campusServiceV2;

    // 캠퍼스 리스트 반환 메소드
    public String makeCampusListCard() {
        List<CampusEntity> campusList = campusServiceV2.getCampusList();
        // 캠퍼스 리스트 객체 생성
        List<ListItemDto> listItems = mappingCampusList(campusList);
        // response 객체 생성
        ResponseDto responseDto = ListCardResponse.mappingResponseDto("어떤 캠퍼스 식당 정보가 궁금해 ?", listItems, null);
        return JsonUtils.toJsonResponse(responseDto);
    }

    // 캠퍼스 리스트 객체 생성
    private List<ListItemDto> mappingCampusList(List<CampusEntity> campusList) {
        List<ListItemDto> listItems = new ArrayList<>();
        for (CampusEntity campus : campusList) {
            String campusName = campus.getCampusNameKo();
            String imageUrl = campus.getThumbnailUrl();
            Map<String, Object> extra = new HashMap<>();
            extra.put("sys_campus_id", campus.getId());

            // 캠퍼스 아이템 객체 생성
            ListItemDto listItem = new ListItemDto(campusName, imageUrl, "block", "66067167cdd882158c759fc2", extra);
            listItems.add(listItem);
        }
        return listItems;
    }
}
