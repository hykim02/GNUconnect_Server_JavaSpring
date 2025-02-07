package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.JsonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CafeteriaControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final CampusServiceV2 campusServiceV2;
    private final CafeteriaServiceV2 cafeteriaServiceV2;

    public CafeteriaControllerV2(
            UserServiceV2 userServiceV2,
            CampusServiceV2 campusServiceV2,
            CafeteriaServiceV2 cafeteriaServiceV2) {
        this.userServiceV2 = userServiceV2;
        this.campusServiceV2 = campusServiceV2;
        this.cafeteriaServiceV2 = cafeteriaServiceV2;
    }


    // 사용자 존재 여부에 따라 응답
    @PostMapping("/api/spring/cafeteria/v2")
    public String responseCafeteriaOrCampusListCard(@RequestBody RequestDto requestDto) {
        // userId로 campusId 찾기
        String userId = requestDto.getUserRequest().getUser().getId();
        int campusId = userServiceV2.getUserCampusId(userId);
        int sysCampusId = requestDto.getAction().getClientExtra().getSys_campus_id();

        // 사용자가 존재 & 식당 블록에서 캠퍼스 눌렀을 때 -> 식당 리스트
        if (campusId != -1 && sysCampusId != -1) {
            return makeCafeteriaListCard(campusId);
        } else { // 사용자가 존재 X & 식당 블록에서 더보기 버튼 눌렀을 때 -> 캠퍼스 리스트
            return makeCampusListCard();
        }
    }


    // 식당 리스트 반환 메소드
    public String makeCafeteriaListCard(int campusId) {
        String campusName = campusServiceV2.findUserCampusName(campusId);
        List<Object[]> cafeteriaList = cafeteriaServiceV2.findCafeteriaList(campusId);

        // 식당 리스트 객체 생성
        List<ListItemDto> listItems = mappingCafeteriaList(campusName, cafeteriaList);
        // response 객체 생성
        ResponseDto responseDto = mappingResponseDto("어떤 교내 식당 정보가 알고싶어 ?", listItems, mappingButtonDto());

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


    // 캠퍼스 리스트 반환 메소드
    public String makeCampusListCard() {
        List<CampusEntity> campusList = campusServiceV2.findCampusList();

        // 캠퍼스 리스트 객체 생성
        List<ListItemDto> listItems = mappingCampusList(campusList);
        // response 객체 생성
        ResponseDto responseDto = mappingResponseDto("어떤 캠퍼스 식당 정보가 궁금해 ?", listItems, null);

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

            // 캠퍼스 아이템 객체 생성
            ListItemDto listItem = new ListItemDto(campusName, imageUrl, "block", "66067167cdd882158c759fc2", extra);
            listItems.add(listItem);
        }
        return listItems;
    }


    // ResponseDto 객체 생성
    public ResponseDto mappingResponseDto(String msg, List<ListItemDto> listItems, List<ButtonDto> buttonDto) {
        // header 객체 생성
        HeaderDto header = new HeaderDto(msg);
        // ListCard 객체 생성
        ListCardDto listCardDto = new ListCardDto(header, listItems, buttonDto);
        // component 객체 생성
        ComponentDto componentDto = new ComponentDto(listCardDto);
        // template 객체 생성
        List<ComponentDto> outputs = new ArrayList<>();
        outputs.add(componentDto);
        TemplateDto templateDto = new TemplateDto(outputs);
        // response 객체 생성
        return new ResponseDto("2.0", templateDto);
    }
}
