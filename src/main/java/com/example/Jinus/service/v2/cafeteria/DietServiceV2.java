package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.request.HandleRequestDto;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DietServiceV2 {
    private final DietRepositoryV2 dietRepositoryV2;
    private final UserRepositoryV2 userRepositoryV2;
    private final CampusRepositoryV2 campusRepositoryV2;
    private final CafeteriaRepositoryV2 cafeteriaRepositoryV2;
    private final UserServiceV2 userServiceV2;

    // request data 받아오기
    public String requestHandler(RequestDto requestDto) {
        String kakaoId = requestDto.getUserRequest().getUser().getId();

        // 현재 시간 파악
        LocalTime time = getCurrentTime();

        // 요청 일반 파라미터 추출 (-> null이면 값 초기화하기)
        String campusName = getOrDefault(
                requestDto.getAction().getParams().getSys_campus_name(), getCampusName(kakaoId));
        String day = getOrDefault(
                requestDto.getAction().getParams().getSys_date(), getDay(time)); // 오늘/내일
        String period = getOrDefault(
                requestDto.getAction().getParams().getSys_time_period(), getPeriodOfDay(time)); // 아침/점심/저녁
        // 요청 필수 파라미터 추출
        String cafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();

        // 요청 파라미터 객체 생성
        HandleRequestDto parameters = new HandleRequestDto(kakaoId, campusName, day, period, cafeteriaName);

        return makeParametersToFindDiet(parameters);
    }

    // 식단 데이터 찾기 위해 필요한 파라미터들 초기화
    // 식당 id, 날짜, 식사 시간대(아침/점심/저녁)
    private String makeParametersToFindDiet(HandleRequestDto parameters) {
        int campusId = userServiceV2.getUserCampusId(parameters.getKakaoId());

        // sysDay로 식단 날짜 설정하기
        String dietDate = getCurrentDateTime(parameters.getDay());
        String dietDescription = getDietDescription(parameters, campusId, dietDate);
        // 식당 imgUrl 찾기
        String imgUrl = getImageUrl(campusId);

        String title = "\uD83C\uDF71 " + parameters.getCafeteriaName() + "(" + parameters.getCampusName().substring(0,2) + ") 메뉴";
        String description = dietDate + "\n\n" + dietDescription;

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


    // Null 체크 후 기본값 설정
    private String getOrDefault(String defaultValue, String getValue) {
        return (defaultValue != null) ? defaultValue : getValue;
    }


    // cafeteria_id 존재여부 확인(캠퍼스에 식당이 존재하는지)
    public String getDietDescription(HandleRequestDto parameters, int campusId,String dateTime) {
        int cafeteriaId = cafeteriaRepositoryV2.findCafeteriaId(parameters.getCafeteriaName(), campusId).orElse(-1);
        // 존재한다면
        if (cafeteriaId != -1) {
            // 해당 식당 메뉴 찾아오기
            return getDietList(cafeteriaId, parameters.getPeriod(), dateTime).toString();
        } else { // 존재하지 않는다면
            // 오류메시지 반환
            return SimpleTextResponse.simpleTextResponse("\"식당을 찾지 못했어!\\n어떤 캠퍼스에 있는 식당인지 정확히 알려줘!\"");
        }
    }

    // 해당 식당 메뉴 찾아오기
    public StringBuilder getDietList(int cafeteriaId, String period, String dateTime) {
        List<Object[]> dietObject = dietRepositoryV2.findDietList(dateTime, period, cafeteriaId);
        System.out.println("dietObject: " + dietObject);
        System.out.println("dietObject.size: " + dietObject.size());
        MultiValueMap<String, String> dietList = new LinkedMultiValueMap<>(); // 중복 키 허용(값을 리스트로 반환)

        for (Object[] o : dietObject) {
            System.out.println("o: "+ Arrays.toString(o));
            String dishName = (String) o[2];

            String key = (o[0] != null) ? (String)o[0] // dishCategory
                    : (o[1] != null) ? (String) o[1] // dishType
                    : "메뉴";

            dietList.add(key, dishName);
        }
        return processDietList(dietList);
    }

    // 식당 리스트 가공
    public StringBuilder processDietList(MultiValueMap<String, String> dietList) {
        // 키 추출
        Set<String> keys = new TreeSet<>(dietList.keySet()); // 순서 보장 - 오름차순
        StringBuilder description = new StringBuilder();

        for (String key : keys) {
            description.append("[").append(key).append("]").append("\n");
            dietList.get(key).forEach(diet -> description.append(diet).append("\n\n"));
        }
        return description;
    }


    // 일반 파라미터 값 채우기
    // sys_campus_name 파라미터 초기화
    public String getCampusName(String kakaoId) {
        // 학과 등록 여부 확인
        int campusId = userRepositoryV2.findCampusIdById(kakaoId).orElse(-1);
        // 학과 등록한 경우 campusId가 존재함
        if (campusId != -1) {
            return campusRepositoryV2.findCampusNameByCampusId(campusId);
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
    public String getPeriodOfDay(LocalTime time) {
        if (time.isAfter(LocalTime.parse("19:00:00")) && time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (time.isAfter(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("13:30:00"))) {
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
    public String getCurrentDateTime(String sysDate) {
        // 현재 날짜
        LocalDate currentDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate();

        if (sysDate.equals("오늘")) {
            return currentDate.toString();
        } else { // 내일
            LocalDate tomorrowDate = currentDate.plusDays(1);
            return tomorrowDate.toString();
        }
    }

    // 식당 imgUrl 찾기
    public String getImageUrl(int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaUrlByCampusId(campusId);
    }

}
