package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import com.example.Jinus.service.CollegeService;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.service.cafeteria.CafeteriaDietService;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.cafeteria.CampusService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CafeteriaController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final CafeteriaService cafeteriaService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final CollegeService collegeService;
    private final CafeteriaDietService cafeteriaDietService;
    private final CampusService campusService;

//    @PostMapping("/cafeteria")
//    public void handleRequest(@RequestBody String jsonPayload) {
//        // jsonPayload를 출력하여 확인
//        System.out.println("Received JSON Payload: " + jsonPayload);
//    }

    public CafeteriaController(
            CafeteriaService cafeteriaService,
            UserService userService,
            DepartmentService departmentService,
            CollegeService collegeService,
            CafeteriaDietService cafeteriaDietService, CampusService campusService) {
        this.cafeteriaService = cafeteriaService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.collegeService = collegeService;
        this.cafeteriaDietService = cafeteriaDietService;
        this.campusService = campusService;
    }

    @PostMapping("/cafeteria")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        logger.info("CafeteriaController 실행");
        logger.info("handleRequest 실행");

        String paramsCampusName = requestDto.getAction().getParams().getSys_campus_name();
        logger.info("paramsCampusName: {}", paramsCampusName); // 가좌캠퍼스

        // 식당 이름
        String paramsCafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();
        logger.info("paramsCafeteriaName: {}", paramsCafeteriaName); // 가좌본관식당

        // 현재 날짜 시간
        String currentDate = getCurrentDate(); // 2024-05-13
        String currentTime = getCurrentTime(); // 16:43:12
        logger.info("currentDate: {}", currentDate);
        logger.info("currentTime: {}", currentTime);

        String currentDay = getDay(currentTime); // 오늘, 내일

        // 현재 식사 시기
        String currentPeriod = getPeriodOfDay(currentTime); // 아침, 점심, 저녁
        logger.info("currentPeriod: {}", currentPeriod);

        int cafeteriaId = 0;
        HashMap<String, List<String>> categoryMenuMap; // 식단 map

        // 식당 이름 중복되는 경우 campusId 필요
        if (paramsCafeteriaName.equals("학생식당") || paramsCafeteriaName.equals("교직원식당")) {
            int campusId = getUserId(requestDto);

            if (campusId == 0) { // user 존재하지 않는 경우
                return simpleTextResponse(); // 캠퍼스 블록 리턴
            } else { // user 존재하는 경우 (캠퍼스 이름도 필요)
                String campusName = campusService.getCampusName(campusId);
                cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), currentPeriod, cafeteriaId);
                return responseMapping(categoryMenuMap, paramsCafeteriaName,
                        campusName, currentDay, currentPeriod);
            }
        } else { // 나머지는 campusId 필요 없음
            cafeteriaId = cafeteriaService.getCafeteriaIdByName(paramsCafeteriaName);
            categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), currentPeriod, cafeteriaId);
            return responseMapping(categoryMenuMap, paramsCafeteriaName,
                    paramsCampusName, currentDay, currentPeriod);
        }
    }

    public String responseMapping(HashMap<String, List<String>> categoryMenuMap,
                                  String cafeteriaName, String campus, String day, String period) {
        logger.info("resonseMapping 실행");
        ThumbnailDto thumbnailDto = new ThumbnailDto("https://t1.kakaocdn.net/openbuilder/sample/lj3JUcmrzC53YIjNDkqbWK.jpg");

        String title = cafeteriaName + " 메뉴";
        String description = handleCafeteriaDiet(categoryMenuMap, campus, day, period);

        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnailDto);
        ComponentDto componentDto = new ComponentDto(basicCardDto);

        List<ComponentDto> componentDtoList = new ArrayList<>();
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
    }

    public String handleCafeteriaDiet(HashMap<String, List<String>> categoryMenuMap, String campus, String day, String period) {
        logger.info("handleCafeteriaDiet 실행");

        String menuDescription = campus + " " + day + " " + period + " 메뉴 기준\n\n";

        if (categoryMenuMap.isEmpty()) { // 메뉴가 존재하지 않는다면
            return menuDescription + "메뉴가 존재하지 않습니다.";
        } else { // 메뉴가 존재한다면
            for (Map.Entry<String, List<String>> entry : categoryMenuMap.entrySet()) {
                String categoryName = entry.getKey();
                List<String> cafeteriaDishes = entry.getValue();
                logger.info("categoryName: {}", categoryName);
                logger.info("cafeteriaDietNames: {}", cafeteriaDishes);

                menuDescription = joinAllMenus(cafeteriaDishes, categoryName, menuDescription);
            }
            logger.info("menuDescription: {}", menuDescription);
            return menuDescription;
        }
    }

    public String joinAllMenus(List<String> cafeteriaDishes, String categoryName, String menuDescription) {
        logger.info("joinAllMenus 실행");

        String coveredCategory = "[" + categoryName + "]"; // 카테고리 괄호로 감싸기
        logger.info("coveredCategory: {}", coveredCategory);

        String joinedMenu = String.join(",", cafeteriaDishes); // 메뉴들 컴마로 연결
        logger.info("joinedMenu: {}", joinedMenu);


        return menuDescription + coveredCategory + "\n" + joinedMenu;
    }


    // user 테이블에 userId 존재 여부 확인
    public int getUserId(@RequestBody RequestDto requestDto) {
        logger.info("getUserId 실행");

        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userService.getDepartmentId(userId);
        logger.info("departmentId: {}", departmentId);

        // user 없는 경우
        if (departmentId == -1) {
            logger.info("존재하지 않는 user");
            return 0;
        } else { // user 존재하는 경우
            logger.info("userId: {}", userId);
            int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
            int campusId = collegeService.getCampusId(collegeId);
            logger.info("collegeId: {}", collegeId);
            logger.info("campusId: {}", campusId);
            return campusId;
        }
    }

    // 현재 날짜 출력 함수
    public String getCurrentDate() {
        logger.info("getCurrentDate 실행");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String[] dateTimeParts = currentDateTime.toString().split("T"); // 2024-05-13
        String[] dateSplt = dateTimeParts[0].split("-");
        logger.info("dateSplt: {}", (Object) dateSplt);

        String currentDay = getDay(dateTimeParts[1]);
        logger.info("currentDay: {}", currentDay);

        if (currentDay.equals("오늘")) {
            return dateTimeParts[0];
        } else { // 내일
            int day = Integer.parseInt(dateSplt[2]);
            int tomorrow = day + 1;
            logger.info("tomorrow: {}", tomorrow);
            return dateSplt[0] + "-" + dateSplt[1] + "-" + tomorrow;
        }
    }

    // 현재 시간 출력 함수
    public String getCurrentTime() {
        logger.info("getCurrentTime 실행");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String[] dateTimeParts = currentDateTime.toString().split("T");

        String timePart = dateTimeParts[1]; // 시간 부분
        String[] timeSplt = timePart.split("\\.");

        return timeSplt[0];
    }

    // 시간 범위에 따라 오늘, 내일 판별하는 함수
    public static String getDay(String currentTime) {
        logger.info("getDay 실행");
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("18:59:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // 시간 범위에 따라 아침, 점심, 저녁을 판별하는 함수
    public static String getPeriodOfDay(String currentTime) {
        logger.info("getPeriodOfDay 실행");

        // 현재 시간을 LocalTime 객체로 변환
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (time.isAfter(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("14:30:00"))) {
            return "점심";
        } else if (time.isAfter(LocalTime.parse("14:30:00")) && time.isBefore(LocalTime.parse("19:00:00"))) {
            return "저녁";
        } else {
            return "아침";
        }
    }

    // userId 존재하지 않는 경우 캠퍼스 블록 리턴(예외처리)
    public String simpleTextResponse() {
        logger.info("simpleTextResponse 실행");

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
        logger.info("toJsonResponse 실행");

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
