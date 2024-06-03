package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.utility.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CustomerServiceController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);
    private final UserService userService;
    private final DepartmentService departmentService;

    public CustomerServiceController(UserService userService, DepartmentService departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @PostMapping("/api/spring/clicker")
    public String clicker(@RequestBody RequestDto requestDto) {
        int roomId = requestDto.getAction().getClientExtra().getSys_room_id();
        String imageUrl = String.format("https://zppxqcdwhqqzbwpmcjjt.supabase.co/storage/v1/object/public/clicker/clicker/%s.png", roomId);
        String altText = "클리커 이미지";
        String message = "🟩: 사용 가능\n🟪: 사용한 시간\n🟦: 남은 시간\n\n기본 3시간(최대 4회 연장가능)";
        return clickerResponse(imageUrl, altText, message);
    }

    public String clickerResponse(String imageUrl, String altText, String message) {
        List<ButtonDto> buttons = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("뒤로가기", "block", null, "665dc46a27a8f26cde73d835", null);
        buttons.add(buttonDto);
        return SimpleImageWithTextCardResponse.simpleImageWithTextCardResponse(imageUrl, altText, message, buttons);
    }

    @PostMapping("/api/spring/getUserId")
    public String getUserId(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        return getUserIdResponse(userId);
    }

    @PostMapping("/api/spring/event")
    public String event(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        Boolean isFriend = requestDto.getUserRequest().getUser().getProperties().getIsFriend();
        if (isFriend == null) {
            return SimpleTextResponse.simpleTextResponse("잠깐! 이벤트에 참여하기 위해서는 채널 친구가 돼있어야해!");
        }
        return getUserEventResponse(userId);
    }

    // TODO: 나중에 리팩토링 해야함!!!!
    // 학사일정 존재하지 않는 경우 예외처리
    public String getUserIdResponse(String userId) {
        int departmentId = userService.getDepartmentId(userId);
        String department;
        // 학과 조회
        if (departmentId == -1) {
            department = "학과 인증을 진행하지 않았습니다.";
        } else {
            department = departmentService.getDepartmentKor(departmentId);
        }
        String formattedText = String.format("👀 내 정보 확인 \n\n[아이디]\n%s\n\n[학과]\n%s(%s)", userId, department, departmentId);

        return SimpleTextResponse.simpleTextResponse(formattedText);
    }

    public String getUserEventResponse(String userId) {
        int departmentId = userService.getDepartmentId(userId);
        List<ButtonDto> buttons = new ArrayList<>();
        String title;
        ButtonDto buttonDto;
        // 예외 처리: 학과 인증을 진행하지 않았을 경우
        if (departmentId == -1) {
            title = "잠깐! 이벤트에 참여하기 위해서는 학과 인증을 진행해야해!";
            buttonDto = new ButtonDto("학과 인증하기", "block", null, "6623de277e38b92310022cd8");
            buttons.add(buttonDto);
            return TextCardResponse.textCardResponse(title, buttons);
        } else {
            title = String.format("[이벤트 참가 코드]\n%s", userId);
            buttonDto = new ButtonDto("설문지 폼 작성", "webLink", "https://forms.gle/LhxRbemjoH2auyoe7");
            buttons.add(buttonDto);
        }
        return TextCardResponse.textCardResponse(title, buttons);
    }
}