package com.example.Jinus.service.diet;

import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.dto.request.DetailParamsItemFieldDto;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.cafeteria.CampusService;
import com.example.Jinus.service.userInfo.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DietParameterService {

    private final UserService userServiceV2;
    private final CampusService campusServiceV2;

    // 요청 파라미터 객체 생성
    public HandleRequestDto setParameters(String kakaoId, LocalTime time, RequestDto requestDto) {
        // 요청 필수 파라미터 추출
        String cafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();

        // DetailParams에서 일반 파라미터 추출 및 초기화(null 체크 포함)
        String campusNameValue = extractValue(requestDto.getAction().getDetailParams().getSys_campus_name())
                .orElseGet(() -> getCampusName(kakaoId));
        String dayValue = extractValue(requestDto.getAction().getDetailParams().getSys_date())
                .orElseGet(() -> getDay(time));
        String periodValue = extractValue(requestDto.getAction().getDetailParams().getSys_time_period())
                .orElseGet(() -> getPeriodOfDay(time));

        // 오늘, 내일 문자열로 날짜 생성
        Date dietDate = getCurrentDate(dayValue);
        // 요청 파라미터 객체 생성
        return new HandleRequestDto(kakaoId, campusNameValue, dayValue, periodValue, cafeteriaName, dietDate);
    }

    // 일반 파라미터가 null인 경우 기본값 설정
    private Optional<String> extractValue(DetailParamsItemFieldDto fieldDto) {
        return Optional.ofNullable(fieldDto).map(DetailParamsItemFieldDto::getValue);
    }

    // 일반 파라미터 값 채우기
    // sys_campus_name 파라미터 초기화
    private String getCampusName(String kakaoId) {
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
    private String getDay(LocalTime time) {
        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("19:00:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // sys_period 파라미터 초기화 - 시간 범위에 따라 아침, 점심, 저녁을 판별
    private String getPeriodOfDay(LocalTime time) {
        if (time.isAfter(LocalTime.parse("19:00:00")) || time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (!time.isBefore(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("13:30:00"))) {
            return "점심";
        } else {
            return "저녁";
        }
    }

    // sysDay 파라미터 값으로 조회할 날짜 찾는 함수
    private Date getCurrentDate(String sysDate) {
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
