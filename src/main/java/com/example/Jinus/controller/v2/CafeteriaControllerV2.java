package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class CafeteriaControllerV2 {

    private final UserServiceV2 userServiceV2;
    private final CafeteriaServiceV2 cafeteriaServiceV2;

    // 사용자 존재 여부에 따라 응답
    @PostMapping("/v2/cafeteria")
    public String responseCafeteriaOrCampusListCard(@RequestBody RequestDto requestDto) {
        // userId로 campusId 찾기
        String userId = requestDto.getUserRequest().getUser().getId();
        int userCampusId = userServiceV2.getUserCampusId(userId);
        int sysCampusId = requestDto.getAction().getClientExtra().getSys_campus_id();

        return cafeteriaServiceV2.campusOrCafeteria(userCampusId, sysCampusId);
    }
}