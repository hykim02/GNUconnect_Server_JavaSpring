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

    // 사용자 발화에 따른 response json 매핑
    public String responseMapping(RequestDto requestDto) {
        List<HashMap<String, String>> academicList = academicService.getAcademicCalendar(requestDto);
        List<String> descriptionList = new ArrayList<>();
        String descriptions;
        String responseDto;

        // 해당 월의 학사일정이 존재하는 경우
        if (!academicList.isEmpty()) {
            descriptions = academicService.handleAcademicList(academicList, descriptionList);
            responseDto = handleResponse(academicList, descriptions, requestDto);
        } else { // 일정 존재하지 않는 경우
            String msg = "해당 월에는 학사일정이 없어\uD83D\uDE05";
            responseDto = SimpleTextResponse.simpleTextResponse(msg);
        }
        return responseDto;
    }


    // 학사일정 return 블록 생성 함수
    public String handleResponse(List<HashMap<String, String>> academicList,
                                 String descriptions,
                                 RequestDto requestDto) {
        List<ButtonDto> buttons = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("더보기", "webLink", "https://www.gnu.ac.kr/main/ps/schdul/selectSchdulMainList.do");
        buttons.add(buttonDto);

        String currentYear = academicService.getYear(academicList);
        int currentMonth = academicService.getCurrentMonth(requestDto);
        String title = currentYear + "년 " + currentMonth + "월 학사일정";

        TextCardDto textCardDto = new TextCardDto(title, descriptions, buttons);

        List<ComponentDto> components = new ArrayList<>();
        ComponentDto componentDto = new ComponentDto(textCardDto);
        components.add(componentDto);

        // quickReplies
        List<QuickReplyDto> quickReplies = new ArrayList<>();
        quickReplies = handleQuickReplies(quickReplies, currentMonth);

        TemplateDto templateDto = new TemplateDto(components, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }

    // quickReplies 생성 (월 생성)
    public List<QuickReplyDto> handleQuickReplies(List<QuickReplyDto> quickReplies, int currentMonth) {
        for (int i = 1; i <= 4; i++) {
            int replyMonth = ++ currentMonth;

            if (replyMonth == 13) {
                currentMonth = 0;
                ++ currentMonth;
                replyMonth = currentMonth;
            }
            String label = replyMonth + "월";
            String msg = label + " 학사일정";
            QuickReplyDto quickReplyDto = new QuickReplyDto(label, "message", msg);
            quickReplies.add(quickReplyDto);
        }
        return quickReplies;
    }
}
