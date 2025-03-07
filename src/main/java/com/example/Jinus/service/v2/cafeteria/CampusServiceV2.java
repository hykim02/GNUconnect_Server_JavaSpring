package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampusServiceV2 {
    private final CampusRepositoryV2 campusRepositoryV2;

    @Autowired
    public CampusServiceV2(CampusRepositoryV2 campusRepositoryV2) {
        this.campusRepositoryV2 = campusRepositoryV2;
    }

    // 사용자의 campusId 받아 캠퍼스 이름 찾기
    public String getUserCampusName(int campusId) {
        return campusRepositoryV2.findCampusNameByCampusId(campusId);
    }

    // id < 5인 캠퍼스 찾아 리스트로 반환
    public List<CampusEntity> getCampusList() {
        return campusRepositoryV2.findCampusList();
    }

    // 캠퍼스 이름으로 id 찾기
    public int getCampusId(String campusName) {
        return campusRepositoryV2.findCampusIdByName(campusName);
    }

    // 캠퍼스 리스트 반환 메소드
    public String makeCampusListCard() {
        List<CampusEntity> campusList = getCampusList();

        // 캠퍼스 리스트 객체 생성
        List<ListItemDto> listItems = mappingCampusList(campusList);
        // response 객체 생성
        ResponseDto responseDto = ListCardResponse.mappingResponseDto("어떤 캠퍼스 식당 정보가 궁금해 ?", listItems, null);

        return JsonUtils.toJsonResponse(responseDto);
    }

    // 캠퍼스 리스트 객체 생성
    public List<ListItemDto> mappingCampusList(List<CampusEntity> campusList) {
        List<ListItemDto> listItems = new ArrayList<>();
        for (CampusEntity campus : campusList) {
            String campusName = campus.getCampusNameKo();
            String imageUrl = campus.getThumbnailUrl();
            Map<String, Object> extra = new HashMap<>();
            extra.put("sys_campus_id", campus.getId());
            System.out.println("campusId: " + campus.getId());

            // 캠퍼스 아이템 객체 생성
            ListItemDto listItem = new ListItemDto(campusName, imageUrl, "block", "66067167cdd882158c759fc2", extra);
            listItems.add(listItem);
        }
        return listItems;
    }
}
