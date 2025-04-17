package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import com.example.Jinus.utility.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DietServiceV2 {

    private final CampusServiceV2 campusServiceV2;
    private final CafeteriaQueryServiceV2 cafeteriaQueryServiceV2;
    private final DietParameterServiceV2 dietParameterServiceV2;
    private final DietResponseServiceV2 dietResponseServiceV2;
    private final DietQueryServiceV2 dietQueryServiceV2;

    // 식단 데이터 찾기 위해 필요한 파라미터 추출 및 초기화
    public String requestHandler(RequestDto requestDto) {
        String kakaoId = requestDto.getUserRequest().getUser().getId();
        // 현재 시간 파악
        LocalTime time = getCurrentTime();
        // 사용자 발화에서 파라미터 추출 및 객체 생성
        HandleRequestDto parameters = dietParameterServiceV2.setParameters(kakaoId, time, requestDto);

        return checkIsCafeteriaInCampus(parameters);
    }

    // 식당 존재 여부에 따른 메뉴 조회 로직
    private String checkIsCafeteriaInCampus(HandleRequestDto parameters) {
        int campusId = campusServiceV2.getCampusId(parameters.getCampusName());
        int cafeteriaId = cafeteriaQueryServiceV2.getCafeteriaId(parameters.getCafeteriaName(), campusId);

        // 캠퍼스에 식당이 존재하지 않는 경우
        if (cafeteriaId == -1) {
            return dietResponseServiceV2.errorMsgThereIsNoCafeteria();
        }
        // 캠퍼스에 식당이 존재하는 경우
        String diets = dietQueryServiceV2.getDietResponse(parameters, cafeteriaId);
        return makeContents(parameters, cafeteriaId, diets);
    }


    // 응답 내용 생성
    private String makeContents(HandleRequestDto parameters, int cafeteriaId, String diets) {
        // 식당 img 찾기
        String imgUrl = cafeteriaQueryServiceV2.getImgUrl(cafeteriaId);

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

        return dietResponseServiceV2.mappingResponse(parameters, imgUrl, title, description);
    }


    // 현재 시간 출력 함수
    private LocalTime getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T");
        String[] timeSplit = dateTimeParts[1].split("\\.");

        return LocalTime.parse(timeSplit[0]);
    }
}
