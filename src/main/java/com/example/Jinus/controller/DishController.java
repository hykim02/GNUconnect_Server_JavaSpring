package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DishController {
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

    public DishController(
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
    @PostMapping("/api/spring/dish")
    public String handleRequest(@RequestBody RequestDto requestDto) {
//        logger.info("CafeteriaController 실행");
//        logger.info("handleRequest 실행");

        // params 받아오기
        // 날짜 (오늘, 내일)
        String paramsDate = requestDto.getAction().getParams().getSys_date();
//        logger.info("paramsDate: {}", paramsDate);
        // 캠퍼스 이름 (동의어 사용)
        String paramsCampusName = requestDto.getAction().getParams().getSys_campus_name();
//        logger.info("paramsCampusName: {}", paramsCampusName); // 가좌캠퍼스

        // 식당 이름 (동의어 사용)
        String paramsCafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();
//        logger.info("paramsCafeteriaName: {}", paramsCafeteriaName); // 가좌본관식당

        int campusId = getUserId(requestDto);
        int cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
        // 캠퍼스 값이 존재하는 경우
        if (paramsCampusName != null) {
//            logger.info("campusId 변경");
            campusId = campusService.getCampusId(paramsCampusName);

//            logger.info("cafeteriaId 변경");
            cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
        } else if (campusId != 0) { // 캠퍼스 값이 없는 경우 & 학과 인증된 경우
//            logger.info("paramsCampusName 변경");
            paramsCampusName = campusService.getCampusName(campusId);
        }
        // 학과 인증 하지않은 경우
        else if (paramsCafeteriaName.equals("교직원식당") || paramsCafeteriaName.equals("학생식당")) { // 식당 중복된 경우
            return responseSimpleTextExp("캠퍼스와 함께 다시 입력해주세요!");
        } else {
//            logger.info("cafeteriaId 변경");
            cafeteriaId = cafeteriaService.getCafeteriaIdByName(paramsCafeteriaName);

//            logger.info("campusId 변경");
            campusId = cafeteriaService.getCampusIdByName(paramsCafeteriaName);

//            logger.info("paramsCampusName 변경");
            paramsCampusName = campusService.getCampusName(campusId);
        }

//        logger.info("cafeteriaId: {}", cafeteriaId);
//        logger.info("campusId: {}", campusId);
//        logger.info("paramsCampusName: {}", paramsCampusName);

        // 캠퍼스에 해당 식당이 존재하지 않는 경우 예외처리
        if (cafeteriaId == 0) {
            return responseSimpleTextExp(paramsCampusName + "에 " + paramsCafeteriaName +"이 존재하지 않습니다." +
                    " 다시 입력해주세요!");
        }

        // 시점 (아침, 점심, 저녁)
        String paramsPeriod = requestDto.getAction().getParams().getSys_time_period();
//        logger.info("paramsPeriod: {}", paramsPeriod);

        // 때에 맞는 식단 조회를 위한 시간 및 날짜 조회
        // 현재 날짜 시간
        String currentTime = getCurrentTime(); // 16:43:12
//        logger.info("currentTime: {}", currentTime);

        String currentDay = getDay(currentTime); // 오늘, 내일

        // 현재 식사 시기(사용자가 입력하지 않았을 경우)
        if (paramsPeriod == null) {
            paramsPeriod = getPeriodOfDay(currentTime); // 아침, 점심, 저녁
//            logger.info("paramsPeriod: {}", paramsPeriod);
        }

        // 사용자의 입력값이 존재하는 경우
        // 날짜
        String detailParamsDate = "";
        if (paramsDate.equals("sys.date")) {
            detailParamsDate = requestDto.getAction().getDetailParams().getSys_date().getOrigin();
//            logger.info("detailParamsDate: {}", detailParamsDate);
        }

        String currentDate = getCurrentDate(detailParamsDate); // 2024-05-13
//        logger.info("currentDate: {}", currentDate);

        // 식사 시기
        String detailParamsPeriod = "";
        if (paramsPeriod.equals("sys.time.period")) {
            detailParamsPeriod = requestDto.getAction().getDetailParams().getSys_time_period().getOrigin();
//            logger.info("detailParamsPeriod: {}", detailParamsPeriod);
        }

        return getCafeteriaData(detailParamsDate, detailParamsPeriod, currentDate,
                paramsPeriod, cafeteriaId, paramsCafeteriaName, paramsCampusName, currentDay);
    }



    // 식단 데이터 조회
    public String getCafeteriaData(String detailParamsDate, String detailParamsPeriod,
                                   String currentDate, String paramsPeriod, int cafeteriaId,
                                   String paramsCafeteriaName, String paramsCampusName,
                                   String currentDay) {

        HashMap<String, List<String>> categoryMenuMap; // 식단 map

        // 사용자의 입력값 없는 경우(식당 블록 리턴하는 경우) -> paramsCampus 값은 항상 존재
        if (detailParamsDate.isEmpty() && detailParamsPeriod.isEmpty()) {
            categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), paramsPeriod, cafeteriaId);

            return responseMapping(cafeteriaId, categoryMenuMap, paramsCafeteriaName,
                    paramsCampusName, currentDay, paramsPeriod);

        } else { // 사용자의 입력값 있는 경우(직접 입력하는 경우)
            // date값이 오늘이나 내일이 아닌 경우 -> 예외처리
            if (!detailParamsDate.isEmpty() && !(detailParamsDate.equals("오늘") || detailParamsDate.equals("내일"))) {
                return responseSimpleTextExp("오늘과 내일의 식단만 조회할 수 있습니다.");

            } else if (!detailParamsDate.isEmpty() && !detailParamsPeriod.isEmpty()) { // 둘 다 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), detailParamsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap, paramsCafeteriaName,
                        paramsCampusName, detailParamsDate, detailParamsPeriod);

            } else if (!detailParamsDate.isEmpty()) { // date값만 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), paramsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap, paramsCafeteriaName,
                        paramsCampusName, detailParamsDate, paramsPeriod);

            } else { // period 값만 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), detailParamsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap, paramsCafeteriaName,
                        paramsCampusName, currentDay, detailParamsPeriod);
            }
        }
    }

    public String responseMapping(int cafeteriaId, HashMap<String, List<String>> categoryMenuMap,
                                  String cafeteriaName, String campus, String day, String period) {
//        logger.info("resonseMapping 실행");
        String url = cafeteriaService.getCampusThumnail(cafeteriaId);
        ThumbnailDto thumbnailDto = new ThumbnailDto(url);

        String title = cafeteriaName + " 메뉴";
        String description = handleCafeteriaDiet(categoryMenuMap, campus, day, period);

        ArrayList<ButtonDto> buttons = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("공유하기", "share");
        buttons.add(buttonDto);

        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnailDto, buttons);
        ComponentDto componentDto = new ComponentDto(basicCardDto);

        List<ComponentDto> componentDtoList = new ArrayList<>();
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
    }

    public String handleCafeteriaDiet(HashMap<String, List<String>> categoryMenuMap, String campus, String day, String period) {
//        logger.info("handleCafeteriaDiet 실행");

        String menuDescription = campus + " " + day + " " + period + " 메뉴 기준\n\n";

        if (categoryMenuMap.isEmpty()) { // 메뉴가 존재하지 않는다면
            return menuDescription + "메뉴가 존재하지 않습니다.";
        } else { // 메뉴가 존재한다면
            for (Map.Entry<String, List<String>> entry : categoryMenuMap.entrySet()) {
                String categoryName = entry.getKey();
                List<String> cafeteriaDishes = entry.getValue();
//                logger.info("categoryName: {}", categoryName);
//                logger.info("cafeteriaDietNames: {}", cafeteriaDishes);

                menuDescription = joinAllMenus(cafeteriaDishes, categoryName, menuDescription);
            }
//            logger.info("menuDescription: {}", menuDescription);
            return menuDescription;
        }
    }

    public String joinAllMenus(List<String> cafeteriaDishes, String categoryName, String menuDescription) {
//        logger.info("joinAllMenus 실행");

        String coveredCategory = "[" + categoryName + "]"; // 카테고리 괄호로 감싸기
//        logger.info("coveredCategory: {}", coveredCategory);

        String joinedMenu = String.join(",", cafeteriaDishes); // 메뉴들 컴마로 연결
//        logger.info("joinedMenu: {}", joinedMenu);

        return menuDescription + coveredCategory + "\n" + joinedMenu + "\n\n";
    }


    // simpleText 예외처리
    public String responseSimpleTextExp(String text) {
        SimpleTextDto simpleTextDto = new SimpleTextDto(text);
        ComponentDto componentDto = new ComponentDto(simpleTextDto);

        List<ComponentDto> componentDtoList = new ArrayList<>();
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
    }


    // user 테이블에 userId 존재 여부 확인
    public int getUserId(@RequestBody RequestDto requestDto) {
//        logger.info("getUserId 실행");

        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userService.getDepartmentId(userId);
//        logger.info("departmentId: {}", departmentId);

        // user 없는 경우
        if (departmentId == -1) {
//            logger.info("존재하지 않는 user");
            return 0;
        } else { // user 존재하는 경우
//            logger.info("userId: {}", userId);
            int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
            int campusId = collegeService.getCampusId(collegeId);
//            logger.info("collegeId: {}", collegeId);
//            logger.info("campusId: {}", campusId);
            return campusId;
        }
    }

    // 조회할 날짜 찾는 함수
    public String getCurrentDate(String currentDay) {
//        logger.info("getCurrentDate 실행");
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T"); // 2024-05-13
        String[] dateSplt = dateTimeParts[0].split("-");
//        logger.info("dateSplt: {}", (Object) dateSplt);

        if (currentDay.isEmpty()) {
            currentDay = getDay(dateTimeParts[1]); // 오늘, 내일
        }
//        logger.info("currentDay: {}", currentDay);

        if (currentDay.equals("오늘")) {
            return dateTimeParts[0];
        } else { // 내일
            int day = Integer.parseInt(dateSplt[2]);
            int tomorrow = day + 1;
//            logger.info("tomorrow: {}", tomorrow);
            return dateSplt[0] + "-" + dateSplt[1] + "-" + tomorrow;
        }
    }

    // 현재 시간 출력 함수
    public String getCurrentTime() {
//        logger.info("getCurrentTime 실행");
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T");

        String timePart = dateTimeParts[1]; // 시간 부분
        String[] timeSplt = timePart.split("\\.");

        return timeSplt[0];
    }

    // 시간 범위에 따라 오늘, 내일 판별하는 함수
    public static String getDay(String currentTime) {
//        logger.info("getDay 실행");
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("18:59:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // 시간 범위에 따라 아침, 점심, 저녁을 판별하는 함수
    public static String getPeriodOfDay(String currentTime) {
//        logger.info("getPeriodOfDay 실행");

        // 현재 시간을 LocalTime 객체로 변환
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (time.isAfter(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("13:30:00"))) {
            return "점심";
        } else if (time.isAfter(LocalTime.parse("13:30:00")) && time.isBefore(LocalTime.parse("19:00:00"))) {
            return "저녁";
        } else {
            return "아침";
        }
    }

    // ObjectMapper를 사용하여 ResponseDto 객체를 JSON 문자열로 변환
    public String toJsonResponse(ResponseDto responseDto) {
//        logger.info("toJsonResponse 실행");

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
