package com.example.Jinus.utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class DateUtils {
    // 주어진 날짜 문자열에 대해 요일을 한글로 반환하는 메서드
    public static String getDayOfWeekInKorean(Date date) {
        java.util.Date utilDate = new java.util.Date(date.getTime());
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 요일 가져오기
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        // 요일을 한글로 변환하는 매핑 (월, 화, 수, ...)
        Map<DayOfWeek, String> koreanDays = Map.of(
                DayOfWeek.MONDAY, "월",
                DayOfWeek.TUESDAY, "화",
                DayOfWeek.WEDNESDAY, "수",
                DayOfWeek.THURSDAY, "목",
                DayOfWeek.FRIDAY, "금",
                DayOfWeek.SATURDAY, "토",
                DayOfWeek.SUNDAY, "일"
        );

        // 변환된 요일 반환
        return koreanDays.get(dayOfWeek);
    }
}
