package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.CollegeService;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CafeteriaController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final CafeteriaService cafeteriaService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final CollegeService collegeService;

//    @PostMapping("/cafeteria")
//    public void handleRequest(@RequestBody String jsonPayload) {
//        // jsonPayload를 출력하여 확인
//        System.out.println("Received JSON Payload: " + jsonPayload);
//    }

    public CafeteriaController(
            CafeteriaService cafeteriaService,
            UserService userService,
            DepartmentService departmentService,
            CollegeService collegeService) {
        this.cafeteriaService = cafeteriaService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.collegeService = collegeService;
    }

    @PostMapping("/cafeteria")
    public void handleRequest(@RequestBody RequestDto requestDto) {
        logger.info("CafeteriaController 실행");
        logger.info("handleRequest 실행");
        String paramsCafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();
        logger.info("paramsCafeteriaName: {}", paramsCafeteriaName);

        int cafeteriaId;

        // 식당 이름 중복되는 경우 campusId 필요
        if (paramsCafeteriaName.equals("학생식당") || paramsCafeteriaName.equals("교직원식당")) {
            int campusId = getUserId(requestDto);
            cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
        } else { // 나머지는 campusId 필요 없음
            cafeteriaId = cafeteriaService.getCafeteriaIdByName(paramsCafeteriaName);
        }

        logger.info("cafeteriaId: {}", cafeteriaId);
    }

    // user 테이블에 userId 존재 여부 확인
    public int getUserId(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userService.getDepartmentId(userId);
        logger.info("departmentId: {}", departmentId);
        int campusId = 0;
        // user 없는 경우
        if (departmentId == -1) {
            logger.info("존재하지 않는 user");
            simpleTextResponse(); // 캠퍼스 선택 블록 리턴
        } else { // user 존재하는 경우
            logger.info("userId: {}", userId);
            int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
            campusId = collegeService.getCampusId(collegeId);
            logger.info("collegeId: {}", collegeId);
        }
        logger.info("campusId: {}", campusId);
        return campusId;
    }

    // userId 존재하지 않는 경우 캠퍼스 블록 리턴(예외처리)
    public String simpleTextResponse() {
        List<ComponentDto> componentDtoList = new ArrayList<>();
        List<ButtonDto> buttonList = new ArrayList<>();
        // 블록 버튼 생성
        ButtonDto buttonDto = new ButtonDto("캠퍼스 선택하기", "block", null,"66067167cdd882158c759fc2");
        buttonList.add(buttonDto);

        TextCardDto textCardDto = new TextCardDto("어떤 캠퍼스의 식당을 찾고있는지 알려주세요 !", buttonList);
        ComponentDto componentDto = new ComponentDto(textCardDto);
        componentDtoList.add(componentDto);
        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
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
