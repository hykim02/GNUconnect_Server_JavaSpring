package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.CollegeService;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.service.cafeteria.CafeteriaDietService;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.cafeteria.CampusService;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
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
        // params 받아오기
        // 날짜 (오늘, 내일)
        String paramsDate = requestDto.getAction().getParams().getSys_date();
        // 캠퍼스 이름 (동의어 사용)
        String paramsCampusName = requestDto.getAction().getParams().getSys_campus_name();
        // 식당 이름 (동의어 사용)
        String paramsCafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();
        // 식당 이름 (원본)
        String originCafeteriaName = requestDto.getAction().getDetailParams().getSys_cafeteria_name().getOrigin();

        int campusId = getUserCampusId(requestDto);
        int cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
        // 캠퍼스 값이 존재하는 경우
        if (paramsCampusName != null) {
            campusId = campusService.getCampusId(paramsCampusName);
            cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(paramsCafeteriaName, campusId);
        } else if (campusId != 0) { // 캠퍼스 값이 없는 경우 & 학과 인증된 경우
            paramsCampusName = campusService.getCampusName(campusId);
        }
        // 학과 인증 하지않은 경우
        else if (paramsCafeteriaName.equals("교직원식당") || paramsCafeteriaName.equals("학생식당")) { // 식당 중복된 경우
            return SimpleTextResponse.simpleTextResponse("캠퍼스와 함께 알려줘 !");
        } else {
            cafeteriaId = cafeteriaService.getCafeteriaIdByName(paramsCafeteriaName);
            campusId = cafeteriaService.getCampusIdByName(paramsCafeteriaName);
            paramsCampusName = campusService.getCampusName(campusId);
        }

        // 시점 (아침, 점심, 저녁)
        String paramsPeriod = requestDto.getAction().getParams().getSys_time_period();

        // 때에 맞는 식단 조회를 위한 시간 및 날짜 조회
        // 현재 날짜 시간
        String currentTime = getCurrentTime(); // 16:43:12
        String currentDay = getDay(currentTime); // 오늘, 내일

        // 현재 식사 시기(사용자가 입력하지 않았을 경우)
        if (paramsPeriod == null) {
            paramsPeriod = getPeriodOfDay(currentTime); // 아침, 점심, 저녁
        }

        // 사용자의 입력값이 존재하는 경우
        // 날짜
        String detailParamsDate = "";
        if (paramsDate.equals("sys.date")) {
            detailParamsDate = requestDto.getAction().getDetailParams().getSys_date().getOrigin();
        }

        String currentDate = getCurrentDate(detailParamsDate); // 2024-05-13

        // 식사 시기
        String detailParamsPeriod = "";
        if (paramsPeriod.equals("sys.time.period")) {
            detailParamsPeriod = requestDto.getAction().getDetailParams().getSys_time_period().getOrigin();
        }

        // 캠퍼스에 해당 식당이 존재하지 않는 경우 예외처리
        if (cafeteriaId == 0) {
            // 칠암캠이면 한번더조회
            if (campusId == 2) {
                paramsCampusName = "칠암(의대,간호대)캠퍼스";
                cafeteriaId = 3;
            } else {
                String msg = paramsCampusName + "에 " + originCafeteriaName +"이(가) 존재하지 않아." +
                        " 다시 입력해줘!";
                return SimpleTextResponse.simpleTextResponse(msg);
            }
        }

        return getCafeteriaData(detailParamsDate, detailParamsPeriod, currentDate,
                paramsPeriod, cafeteriaId, paramsCampusName, originCafeteriaName, currentDay);
    }


    // 사용자 발화문의 파라미터값에 따른 식단 데이터 조회
    public String getCafeteriaData(String detailParamsDate, String detailParamsPeriod,
                                   String currentDate, String paramsPeriod, int cafeteriaId,
                                   String paramsCampusName, String originCafeteriaName,
                                   String currentDay) {

        HashMap<String, List<String>> categoryMenuMap; // 식단 map

        // 사용자의 입력값 없는 경우(식당 블록 리턴하는 경우) -> paramsCampus 값은 항상 존재
        if (detailParamsDate.isEmpty() && detailParamsPeriod.isEmpty()) {
            categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), paramsPeriod, cafeteriaId);

            return responseMapping(cafeteriaId, categoryMenuMap,
                    paramsCampusName, currentDay, originCafeteriaName, paramsPeriod, currentDate);

        } else { // 사용자의 입력값 있는 경우(직접 입력하는 경우)
            // date값이 오늘이나 내일이 아닌 경우 -> 예외처리
            if (!detailParamsDate.isEmpty() && !(detailParamsDate.equals("오늘") || detailParamsDate.equals("낼") || detailParamsDate.equals("내일"))) {
                return SimpleTextResponse.simpleTextResponse("오늘과 내일의 식단만 조회할 수 있어");

            } else if (!detailParamsDate.isEmpty() && !detailParamsPeriod.isEmpty()) { // 둘 다 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), detailParamsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap,
                        paramsCampusName, detailParamsDate, originCafeteriaName, detailParamsPeriod, currentDate);

            } else if (!detailParamsDate.isEmpty()) { // date값만 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), paramsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap,
                        paramsCampusName, detailParamsDate, originCafeteriaName, paramsPeriod, currentDate);

            } else { // period 값만 입력된 경우
                categoryMenuMap = cafeteriaDietService.getCafeteriaDiet(LocalDate.parse(currentDate), detailParamsPeriod, cafeteriaId);

                return responseMapping(cafeteriaId, categoryMenuMap,
                        paramsCampusName, currentDay, originCafeteriaName, detailParamsPeriod, currentDate);
            }
        }
    }

    // 식단 return 블록 생성
    public String responseMapping(int cafeteriaId, HashMap<String, List<String>> categoryMenuMap,
                                  String campus, String day, String originCafeteriaName, String period, String currentDate) {
        String url = cafeteriaService.getCampusThumnail(cafeteriaId);
        ThumbnailDto thumbnailDto = new ThumbnailDto(url);

        String title = "\uD83C\uDF71" + " " + originCafeteriaName + "(" + campus.substring(0, 2) + ")" + " 메뉴";
        String description = handleCafeteriaDiet(categoryMenuMap, period, currentDate);

        List<ButtonDto> buttons = new ArrayList<>();
        Map<String, Object> extra = new HashMap<>();
        extra.put("cafeteriaId", cafeteriaId);
        extra.put("date", currentDate);
        extra.put("time", period);
        ButtonDto buttonDto1 = new ButtonDto("영양성분 확인하기", "block", "", "66d3d585e0379151197871df", extra);
        buttons.add(buttonDto1);
        ButtonDto buttonDto2 = new ButtonDto("공유하기", "share");
        buttons.add(buttonDto2);

        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnailDto, buttons);
        ComponentDto componentDto = new ComponentDto(basicCardDto);

        List<ComponentDto> componentDtoList = new ArrayList<>();
        componentDtoList.add(componentDto);

        List<QuickReplyDto> quickReplies = new ArrayList<>();
        if (period.equals("아침")) {
            quickReplies.add(new QuickReplyDto("점심", "message", campus+" "+originCafeteriaName+" "+day+" 점심 메뉴"));
            quickReplies.add(new QuickReplyDto("저녁", "message", campus+" "+originCafeteriaName+" "+day+" 저녁 메뉴"));
        } else if (period.equals("점심")) {
            quickReplies.add(new QuickReplyDto("아침", "message", campus+" "+originCafeteriaName+" "+day+" 아침 메뉴"));
            quickReplies.add(new QuickReplyDto("저녁", "message", campus+" "+originCafeteriaName+" "+day+" 저녁 메뉴"));
        } else {
            quickReplies.add(new QuickReplyDto("아침", "message", campus+" "+originCafeteriaName+" "+day+" 아침 메뉴"));
            quickReplies.add(new QuickReplyDto("점심", "message", campus+" "+originCafeteriaName+" "+day+" 점심 메뉴"));
        }

        TemplateDto templateDto = new TemplateDto(componentDtoList, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }

    // 식단 메뉴 블록 내용 정리
    public String handleCafeteriaDiet(HashMap<String, List<String>> categoryMenuMap, String period, String currentDate) {
        String menuDescription = currentDate + " " + period + " 식단\n\n";

        if (categoryMenuMap.isEmpty()) { // 메뉴가 존재하지 않는다면
            return menuDescription + "메뉴가 존재하지 않습니다.";
        } else { // 메뉴가 존재한다면
            for (Map.Entry<String, List<String>> entry : categoryMenuMap.entrySet()) {
                String categoryName = entry.getKey();
                List<String> cafeteriaDishes = entry.getValue();
                menuDescription = joinAllMenus(cafeteriaDishes, categoryName, menuDescription);
            }
            return menuDescription;
        }
    }

    public String joinAllMenus(List<String> cafeteriaDishes, String categoryName, String menuDescription) {
        String coveredCategory = "[" + categoryName + "]"; // 카테고리 괄호로 감싸기
        String joinedMenu = String.join(",", cafeteriaDishes); // 메뉴들 컴마로 연결

        return menuDescription + coveredCategory + "\n" + joinedMenu + "\n\n";
    }

    // 사용자의 캠퍼스 id 조회
    public int getUserCampusId(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        int campusId = userService.getCampusId(userId);

        // user 없는 경우
        if (campusId == -1) {
            return 0;
        } else { // user 존재하는 경우
            return campusId;
        }
    }

    // 조회할 날짜 찾는 함수
    public String getCurrentDate(String currentDay) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate currentDate = currentDateTime.toLocalDate(); // 현재 날짜

        if (currentDay.isEmpty()) {
            currentDay = getDay(currentDateTime.toString().split("T")[1]); // 오늘, 내일을 결정하는 로직이 있는 함수
        }

        if (currentDay.equals("오늘")) {
            return currentDate.toString();
        } else { // 내일
            LocalDate tomorrowDate = currentDate.plusDays(1);
            return tomorrowDate.toString();
        }
    }

    // 현재 시간 출력 함수
    public String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T");

        String timePart = dateTimeParts[1]; // 시간 부분
        String[] timeSplt = timePart.split("\\.");

        return timeSplt[0];
    }

    // 시간 범위에 따라 오늘, 내일 판별하는 함수
    public static String getDay(String currentTime) {
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("18:59:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // 시간 범위에 따라 아침, 점심, 저녁을 판별하는 함수
    public static String getPeriodOfDay(String currentTime) {
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
}
