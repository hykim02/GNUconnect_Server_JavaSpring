package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.userInfo.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class CafeteriaController {

    private final UserService userServiceV2;
    private final CafeteriaService cafeteriaServiceV2;

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