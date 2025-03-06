package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/spring")
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
    @PostMapping("/cafeteria")
    public String responseCafeteriaOrCampusListCard(@RequestBody RequestDto requestDto) {
        // userId로 campusId 찾기
        String userId = requestDto.getUserRequest().getUser().getId();
        int campusId = userServiceV2.getUserCampusId(userId);
        int sysCampusId = requestDto.getAction().getClientExtra().getSys_campus_id();

        return campusOrCafeteria(campusId, sysCampusId);
    }


    // 반환 조건 설정
    private String campusOrCafeteria(int campusId, int sysCampusId) {
        // 더보기 버튼 누른 경우
        if (sysCampusId == -1) {
            return campusServiceV2.makeCampusListCard();
        }

        // 사용자가 원하는 캠퍼스가 있을 때
        if (sysCampusId > 0) {
            return cafeteriaServiceV2.makeCafeteriaListCard(sysCampusId);
        }

        // 사용자가 원하는 캠퍼스가 없을 때
        return (campusId != -1)
                ? cafeteriaServiceV2.makeCafeteriaListCard(campusId)
                : campusServiceV2.makeCampusListCard();
    }


}
