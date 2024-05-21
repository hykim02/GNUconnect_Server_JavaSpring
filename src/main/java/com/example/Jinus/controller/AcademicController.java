package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.academic.AcademicService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AcademicController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final AcademicService academicService;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
    }


    @PostMapping("/api/spring/academic-calendar")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        logger.info("AcademicController 실행");
        logger.info("hadleRequest 실행");

        return responseMapping();
    }

    // response json 매핑
    public String responseMapping() {
        logger.info("responseMapping 실행");
        List<HashMap<String, String>> academicList = getAcademicCalendar();

        List<String> descriptionList = new ArrayList<>();
        String descriptions = "";

        for (HashMap<String, String> academic: academicList) {
            logger.info("academic:{}", academic); // 학사일정 한 개

            String startDate = spltDate(academic.get("start_date")); // 시작 날짜
            String endDate = spltDate(academic.get("end_date")); // 끝 날짜
            String content = academic.get("content");
            logger.info("date: {}", startDate + "~" + endDate);
            logger.info("content: {}", content);

            descriptionList.add(joinAllAcademics(startDate, endDate, content));
            logger.info("descriptionList: {}", descriptionList);
        }

        for (String description: descriptionList) {
            descriptions = descriptions + description;
        }
        logger.info("descriptions: {}", descriptions);

        List<ButtonDto> buttons = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("더보기", "webLink", "https://www.gnu.ac.kr/main/ps/schdul/selectSchdulMainList.do");
        buttons.add(buttonDto);

        int year = LocalDate.now().getYear();
        int month = getCurrentMonth();
        String title = year + "년 " + month + "월 학사일정";
        TextCardDto textCardDto = new TextCardDto(title, descriptions, buttons);

        List<ComponentDto> components = new ArrayList<>();
        ComponentDto componentDto = new ComponentDto(textCardDto);
        components.add(componentDto);

        TemplateDto templateDto = new TemplateDto(components);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
    }

    public static String joinAllAcademics(String startDate, String endDate, String content) {
        logger.info("joinAllAcademics 실행");
        String duration = "[" + startDate + " ~ " + endDate + "]" + "\n";
        return duration + content + "\n\n";
    }

    public static String spltDate(String date) {
        logger.info("spltDate 실행");

        String[] spltDate = date.split(" ");
        String[] spltMonth = spltDate[0].split("-");
        logger.info("spltMonth: {}", spltMonth[1]);
        logger.info("spltMonth: {}", spltMonth[2]);

        return spltMonth[1] + "/" + spltMonth[2];
    }

    public List<HashMap<String, String>> getAcademicCalendar() {
        logger.info("getAcademicCalendar 실행");

        List<HashMap<String, String>> academicList;

        int currentMonth = getCurrentMonth();
        academicList = academicService.getAcademicContents(currentMonth);
        logger.info("academicList: {}", academicList); // 해당 월의 학사일정 (type 1)

        return academicList;
    }

    // 현재 월 출력
    public static int getCurrentMonth() {
        logger.info("getCurrentMonth 실행");
        LocalDate today = LocalDate.now(); // 현재 날짜를 가져옴
        int currentMonth = today.getMonthValue(); // 월을 숫자로 가져옴 (1~12)

        System.out.println("Current month: " + currentMonth);
        return currentMonth;
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
