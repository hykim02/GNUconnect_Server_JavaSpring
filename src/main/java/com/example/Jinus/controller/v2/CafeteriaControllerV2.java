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
            return cafeteriaServiceV2.makeCafeteriaListCard(campusId);
        } else { // 사용자가 존재 X & 식당 블록에서 더보기 버튼 눌렀을 때 -> 캠퍼스 리스트
            return campusServiceV2.makeCampusListCard();
        }
    }
}
