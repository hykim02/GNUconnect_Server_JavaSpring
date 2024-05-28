package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.ComponentDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.dto.response.SimpleTextDto;
import com.example.Jinus.dto.response.TemplateDto;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
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

    @PostMapping("/api/spring/getUserId")
    public String getUserId(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userService.getDepartmentId(userId);
        return responseMapping(userId, departmentId);
    }

    public String responseMapping(String userId, int departmentId) {
        return toJsonResponse(simpleTextResponse(userId, departmentId));
    }

    // 학사일정 존재하지 않는 경우 예외처리
    public ResponseDto simpleTextResponse(String userId, int departmentId) {
        String department;
        // 학과 조회
        if (departmentId == -1) {
            department = "학과 인증을 진행하지 않았습니다.";
        } else {
            department = departmentService.getDepartmentKor(departmentId);
        }
        String formattedText = String.format("👀 내 정보 확인 \n\n[아이디]\n%s\n\n[학과]\n%s(%s)", userId, department, departmentId);
        SimpleTextDto simpleTextDto = new SimpleTextDto(formattedText);
        ComponentDto componentDto = new ComponentDto(simpleTextDto);

        List<ComponentDto> components = new ArrayList<>();
        components.add(componentDto);

        TemplateDto templateDto = new TemplateDto(components);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return responseDto;
    }

    // ObjectMapper를 사용하여 ResponseDto 객체를 JSON 문자열로 변환
    public String toJsonResponse(ResponseDto responseDto) {
        String jsonResponse;
        ObjectMapper objectMapper = new ObjectMapper();

        // null 값 무시 설정
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            jsonResponse = objectMapper.writeValueAsString(responseDto);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 오류가 발생한 경우 처리
            e.printStackTrace();
            jsonResponse = "{}"; // 빈 JSON 응답 반환(오류 메시지 출력하기)
        }

        // jsonResponse를 클라이언트로 보내는 코드
        System.out.println(jsonResponse);
        return jsonResponse;
    }
}