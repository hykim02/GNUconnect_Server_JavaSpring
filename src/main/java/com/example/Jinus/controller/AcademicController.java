package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.academic.AcademicService;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    // post 요청 처리
    @PostMapping("/api/spring/academic-calendar")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        return responseMapping(requestDto);
    }

    // response json 매핑
    public String responseMapping(RequestDto requestDto) {
        List<HashMap<String, String>> academicList = getAcademicCalendar(requestDto);
        List<String> descriptionList = new ArrayList<>();
        String descriptions;
        String responseDto;

        // 해당 월의 학사일정이 존재하는 경우
        if (!academicList.isEmpty()) {
            descriptions = handleAcademicList(academicList, descriptionList);
            responseDto = handleResponse(descriptions, requestDto);
        } else { // 일정 존재하지 않는 경우
            String msg = "해당 월에는 학사일정이 없어";
            responseDto = SimpleTextResponse.simpleTextResponse(msg);
        }
        return responseDto;
    }

    // 학사일정 리스트 개별 처리
    public String handleAcademicList(List<HashMap<String, String>> academicList,
                                           List<String> descriptionList) {
        StringBuilder descriptions = new StringBuilder();

        for (HashMap<String, String> academic: academicList) {
            String startDate = spltDate(academic.get("start_date")); // 시작 날짜
            String endDate = spltDate(academic.get("end_date")); // 끝 날짜
            String content = academic.get("content");
            descriptionList.add(joinAllAcademics(startDate, endDate, content));
        }

        for (String description: descriptionList) {
            descriptions.append(description);
        }

        return descriptions.toString();
    }

    // 학사일정 return 블록
    public String handleResponse(String descriptions, RequestDto requestDto) {
        List<ButtonDto> buttons = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("더보기", "webLink", "https://www.gnu.ac.kr/main/ps/schdul/selectSchdulMainList.do");
        buttons.add(buttonDto);

        int year = LocalDate.now().getYear();
        int currentMonth = getCurrentMonth(requestDto);
        String title = year + "년 " + currentMonth + "월 학사일정";
        TextCardDto textCardDto = new TextCardDto(title, descriptions, buttons);

        List<ComponentDto> components = new ArrayList<>();
        ComponentDto componentDto = new ComponentDto(textCardDto);
        components.add(componentDto);

        // quickReplies
        List<QuickReplyDto> quickReplies = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            int replyMonth = currentMonth + i;

            if (replyMonth == 13) {
                break;
            } else {
                String label = replyMonth + "월";
                String msg = label + " 학사일정";
                QuickReplyDto quickReplyDto = new QuickReplyDto(label, "message", msg);
                quickReplies.add(quickReplyDto);
            }
        }

        TemplateDto templateDto = new TemplateDto(components, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }

    // 월별 학사일정 합치기
    public static String joinAllAcademics(String startDate, String endDate, String content) {
        String duration = "[" + startDate + " ~ " + endDate + "]" + "\n";
        return duration + "\uD83D\uDDD3\uFE0F" + content + "\n\n";
    }

    // 날짜 추출
    public static String spltDate(String date) {
        String[] spltDate = date.split(" ");
        String[] spltMonth = spltDate[0].split("-");

        return spltMonth[1] + "/" + spltMonth[2];
    }

    // db에서 일정 찾아오기
    public List<HashMap<String, String>> getAcademicCalendar(RequestDto requestDto) {
        List<HashMap<String, String>> academicList;
        int currentMonth = getCurrentMonth(requestDto);
        academicList = academicService.getAcademicContents(currentMonth);

        return academicList;
    }

    // 사용자 발화문에 월이 포함되어 있는지 여부 확인
    public String checkUserUtterance(RequestDto requestDto) {
        String userMsg = requestDto.getUserRequest().getUtterance();
        String extractedInt = userMsg.replaceAll("[^0-9]", "");

        if (extractedInt.isEmpty()) {
            return null;
        } else {
            return extractedInt;
        }
    }

    public int getCurrentMonth(RequestDto requestDto) {
        String extractedMonth = checkUserUtterance(requestDto);
        int currentMonth;

        // 현재 월에 해당하는 공지 들고옴
        if (extractedMonth == null) {
            currentMonth = LocalDate.now().getMonthValue();
        } else {
            currentMonth = Integer.parseInt(extractedMonth);
        }
        return currentMonth;
    }
}
