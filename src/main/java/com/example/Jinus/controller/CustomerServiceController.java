package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.ComponentDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.dto.response.SimpleTextDto;
import com.example.Jinus.dto.response.TemplateDto;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
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
        return simpleTextResponse(userId, departmentId);
    }

    // 학사일정 존재하지 않는 경우 예외처리
    public String simpleTextResponse(String userId, int departmentId) {
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
}