package com.example.Jinus.service.academic;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.entity.academic.AcademicEntity;
import com.example.Jinus.repository.academic.AcademicRepository;
import com.example.Jinus.service.CollegeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AcademicService {
    private static final Logger logger = LoggerFactory.getLogger(CollegeService.class);
    private final AcademicRepository academicRepository;

    public AcademicService(AcademicRepository calendarRepository) {
        this.academicRepository = calendarRepository;
    }

    // db에서 일정 찾아오는 함수
    public List<HashMap<String, String>> getAcademicCalendar(RequestDto requestDto) {
        List<HashMap<String, String>> academicList;
        int currentMonth = getCurrentMonth(requestDto);
        academicList = getAcademicContents(currentMonth);

        return academicList;
    }

    // 학사일정 리스트 반환 함수
    public List<HashMap<String, String>> getAcademicContents(int currentMonth) {
        int currentYear = LocalDate.now().getYear();
        List<AcademicEntity> entities = academicRepository.findCalendarEntities(currentMonth, currentYear);
        List<HashMap<String, String>> academicList = new ArrayList<>();

        // 해당 월의 학사일정이 존재하는 경우
        if (entities != null) {
            for (AcademicEntity entity : entities) {
                HashMap<String, String> academicMap = new HashMap<>();

                academicMap.put("type", String.valueOf(entity.getCalendarType()));
                academicMap.put("content", entity.getContent());
                academicMap.put("end_date", entity.getEndDate());
                academicMap.put("start_date", entity.getStartDate());
                academicList.add(academicMap);
            }
        } else { // 해당 월의 학사일정이 존재하지 않는 경우
            logger.debug("db에 해당 월의 학사일정이 존재하지 않음");
            academicList = null;
        }
        return academicList;
    }

    // 현재 월 추출 함수
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

    // 학사일정 리스트 개별 처리
    public String handleAcademicList(List<HashMap<String, String>> academicList,
                                     List<String> descriptionList) {
        StringBuilder descriptions = new StringBuilder();

        for (HashMap<String, String> academic: academicList) {
            String startDate = spltDate(academic.get("start_date")); // 시작 날짜
            String endDate = spltDate(academic.get("end_date")); // 끝 날짜
            String content = academic.get("content");

            if (startDate.equals(endDate)) { // 시작날짜와 끝날짜가 같은 경우 시작날짜만 출력
                descriptionList.add(joinAllAcademics(startDate, null, content));
            } else {
                descriptionList.add(joinAllAcademics(startDate, endDate, content));
            }
        }

        for (String description: descriptionList) {
            descriptions.append(description);
        }

        return descriptions.toString();
    }

    // 해당 월의 학사일정 내용 합치기
    public static String joinAllAcademics(String startDate, String endDate, String content) {
        String duration;

        if (endDate == null) {
            duration = "[" + startDate + "]" + "\n";
        } else {
            duration = "[" + startDate + " ~ " + endDate + "]" + "\n";
        }
        return duration + "\uD83D\uDDD3\uFE0F" + content + "\n\n";
    }

    // 날짜 추출(월/일)
    public static String spltDate(String date) {
        String[] spltDate = date.split(" ");
        String[] spltMonth = spltDate[0].split("-");

        return spltMonth[1] + "/" + spltMonth[2];
    }

    // 년도 추출(올해/다음 년도)
    public String getYear(List<HashMap<String, String>> academicList) {
        HashMap<String, String> first_academic = academicList.getFirst();
        logger.info("first_academic:"+ first_academic);
        String endDate = first_academic.get("end_date"); // 끝 날짜
        return endDate.split(" ")[0].split("-")[0];
    }
}
