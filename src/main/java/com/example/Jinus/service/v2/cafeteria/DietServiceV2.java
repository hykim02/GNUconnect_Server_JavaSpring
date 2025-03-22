package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.request.DetailParamsItemFieldDto;
import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.DateUtils;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DietServiceV2 {
    private final DietRepositoryV2 dietRepositoryV2;
    private final CampusServiceV2 campusServiceV2;
    private final CafeteriaServiceV2 cafeteriaServiceV2;
    private final UserServiceV2 userServiceV2;
    private final CacheServiceV2 dietCacheServiceV2;

    // 식단 데이터 찾기 위해 필요한 파라미터 추출 및 초기화
    public String requestHandler(RequestDto requestDto) {
        String kakaoId = requestDto.getUserRequest().getUser().getId();

        // 현재 시간 파악
        LocalTime time = getCurrentTime();

        // 요청 일반 파라미터 추출
        DetailParamsItemFieldDto campusName = requestDto.getAction().getDetailParams().getSys_campus_name();
        DetailParamsItemFieldDto day = requestDto.getAction().getDetailParams().getSys_date();
        DetailParamsItemFieldDto period = requestDto.getAction().getDetailParams().getSys_time_period();
        // null이면 값 초기화하기
        String campusNameValue = (campusName != null) ? campusName.getValue() : getCampusName(kakaoId);
        String dayValue = (day != null) ? day.getValue() : getDay(time); // 오늘, 내일
        String periodValue = (period != null) ? period.getValue() : getPeriodOfDay(time); // 아침, 점심, 저녁
        // 요청 필수 파라미터 추출
        String cafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();

        // 오늘, 내일 문자열로 날짜 생성
        Date dietDate = getCurrentDate(dayValue);

        // 요청 파라미터 객체 생성
        HandleRequestDto parameters = new HandleRequestDto(kakaoId, campusNameValue, dayValue, periodValue, cafeteriaName, dietDate);

        return makeResponse(parameters);
    }

    // response 생성 로직
    public String makeResponse(HandleRequestDto parameters) {
        int campusId = campusServiceV2.getCampusId(parameters.getCampusName());
        int cafeteriaId = cafeteriaServiceV2.getCafeteriaId(parameters.getCafeteriaName(), campusId);

        // 캠퍼스에 식당이 존재하지 않는 경우
        if (cafeteriaId == -1) {
            return errorMsgThereIsNoCafeteria();
        }

        // 캠퍼스에 식당이 존재하는 경우
        String diets = getDietResponse(parameters, cafeteriaId);
        return makeContents(parameters, cafeteriaId, diets);
    }

    // 메뉴 존재 여부 확인
    private String getDietResponse(HandleRequestDto parameters, int cafeteriaId) {
        // 메뉴 존재하는 경우
        if (checkThereIsDiet(parameters, cafeteriaId) != -1) {
            // 메뉴 찾기
            MultiValueMap<String, String> dietList = getDiets(parameters, cafeteriaId);
            return processDietList(dietList).toString();
        }
        return "\n메뉴가 존재하지 않습니다."; // 메뉴가 없는 경우
    }


    // 캠퍼스에 식당이 존재하지 않는 경우 메시지 출력
    private String errorMsgThereIsNoCafeteria() {
        return SimpleTextResponse
                .simpleTextResponse("식당을 찾지 못했어!\n어떤 캠퍼스에 있는 식당인지 정확히 알려줘!");
    }


    // 식당 메뉴 존재여부 확인
    private int checkThereIsDiet(HandleRequestDto parameters, int cafeteriaId) {
        // 오늘, 내일 문자열로 날짜 설정하기
        Date dietDate = parameters.getDietDate();
        List<DietDto> dietDtos =
                dietRepositoryV2.findDietList(dietDate, parameters.getPeriod(), cafeteriaId);
        return (!dietDtos.isEmpty()) ? 1 : -1;
    }


    // 카테고리별 메뉴 리스트 생성하기
    public MultiValueMap<String, String> getDiets(HandleRequestDto parameters, int cafeteriaId) {
        List<DietDto> dietDtos = dietCacheServiceV2.getDietList(parameters, cafeteriaId);
        MultiValueMap<String, String> dietList = new LinkedMultiValueMap<>(); // 중복 키 허용(값을 리스트로 반환)

        for (DietDto o : dietDtos) {
            String key = (o.getDishCategory() != null) ? o.getDishCategory()
                    : (o.getDishType() != null) ? o.getDishType()
                    : "메뉴";

            dietList.add(key, o.getDishName());
        }
        return dietList;
    }


    // 카테고리별 메뉴 문자열로 나열하기
    public StringBuilder processDietList(MultiValueMap<String, String> dietList) {
        // 키 추출
        Set<String> keys = new TreeSet<>(dietList.keySet()); // 순서 보장 - 오름차순
        StringBuilder description = new StringBuilder();

        for (String key : keys) {
            description.append("\n[").append(key).append("]").append("\n");
            dietList.get(key).forEach(diet -> description.append(diet).append("\n"));
        }
        return description;
    }


    // 응답 내용 초기화
    private String makeContents(HandleRequestDto parameters, int cafeteriaId, String diets) {
        // 식당 img 찾기
        String imgUrl = cafeteriaServiceV2.getImgUrl(cafeteriaId);

        // title 데이터 연결
        String title = "\uD83C\uDF71 " +
                parameters.getCafeteriaName() + "(" +
                parameters.getCampusName().substring(0, 2) + ") 메뉴";

        // 식단 날짜
        Date dietDate = parameters.getDietDate();
        String day = DateUtils.getDayOfWeekInKorean(dietDate);

        // 메뉴 연결
        String description = dietDate + "(" + day + ") " +
                parameters.getPeriod() + "\n" + diets;

        return mappingResponse(parameters, imgUrl, title, description);
    }

    // 응답 객체 매핑
    public String mappingResponse(HandleRequestDto parameters, String imgUrl, String title, String description) {
        // imgUrl 객체 생성
        ThumbnailDto thumbnail = new ThumbnailDto(imgUrl);

        // 버튼 리스트 객체 생성
        List<ButtonDto> buttonList = List.of(new ButtonDto("공유하기", "share"));

        // 아이템 객체 생성
        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnail, buttonList);
        List<ComponentDto> outputs = List.of(new ComponentDto(basicCardDto));

        List<QuickReplyDto> quickReplies = mappingQuickReply(parameters);

        TemplateDto templateDto = new TemplateDto(outputs, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }


    // quickReply 객체 생성
    private List<QuickReplyDto> mappingQuickReply(HandleRequestDto parameters) {
        return switch (parameters.getPeriod()) {
            case "아침" -> List.of(
                    new QuickReplyDto("점심", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 점심 메뉴"),
                    new QuickReplyDto("저녁", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 저녁 메뉴")
            );
            case "점심" -> List.of(
                    new QuickReplyDto("아침", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 아침 메뉴"),
                    new QuickReplyDto("저녁", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 저녁 메뉴")
            );
            default -> List.of(
                    new QuickReplyDto("아침", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 아침 메뉴"),
                    new QuickReplyDto("점심", "message", parameters.getCampusName() + " " + parameters.getCafeteriaName() + " " + parameters.getDay() + " 점심 메뉴")
            );
        };
    }


    // 일반 파라미터 값 채우기
    // sys_campus_name 파라미터 초기화
    public String getCampusName(String kakaoId) {
        // 학과 등록 여부 확인
        int campusId = userServiceV2.getUserCampusId(kakaoId);
        // 학과 등록한 경우 campusId가 존재함
        if (campusId != -1) {
            return campusServiceV2.getUserCampusName(campusId);
        } else { // 학과 등록 안한 경우
            return  "가좌캠퍼스";
        }
    }

    // sys_date 파라미터 초기화 - 시간 범위에 따라 오늘, 내일 판별
    public String getDay(LocalTime time) {
        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("19:00:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // sys_period 파라미터 초기화 - 시간 범위에 따라 아침, 점심, 저녁을 판별
    public static String getPeriodOfDay(LocalTime time) {
        if (time.isAfter(LocalTime.parse("19:00:00")) || time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (!time.isBefore(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("13:30:00"))) {
            return "점심";
        } else {
            return "저녁";
        }
    }

    // 현재 시간 출력 함수
    public LocalTime getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T");
        String[] timeSplit = dateTimeParts[1].split("\\.");

        return LocalTime.parse(timeSplit[0]);
    }


    // sysDay 파라미터 값으로 조회할 날짜 찾는 함수
    public Date getCurrentDate(String sysDate) {
        // 현재 날짜
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1); // 하루 뒤 날짜 계산

        if (sysDate.equals("오늘")) {
            return Date.valueOf(today);
        } else { // 내일
            return Date.valueOf(tomorrow);
        }
    }
}
