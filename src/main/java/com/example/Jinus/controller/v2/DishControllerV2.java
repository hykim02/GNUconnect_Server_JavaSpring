package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.v2.cafeteria.DietServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.SimpleTextResponse;
import org.h2.engine.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/spring")
public class DishControllerV2 {
    private final DietServiceV2 dietServiceV2;
    private final UserServiceV2 userServiceV2;

    public DishControllerV2(DietServiceV2 dietServiceV2,
                            UserServiceV2 userServiceV2) {
        this.dietServiceV2 = dietServiceV2;
        this.userServiceV2 = userServiceV2;
    }

    @PostMapping("/dish/v2")
    public void handleRequest(@RequestBody RequestDto requestDto) {
        String kakaoId = requestDto.getUserRequest().getUser().getId();
        int campusId = userServiceV2.getUserCampusId(kakaoId);

        // 일반 파라미터 null값 여부 확인
        String sysCampusName = requestDto.getAction().getParams().getSys_campus_name();
        String sysDate = requestDto.getAction().getParams().getSys_date();
        String sysPeriod = requestDto.getAction().getParams().getSys_time_period();
        // 필수 파라미터
        String sysCafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name();

        // 현재 시간 파악
        LocalTime time = dietServiceV2.getCurrentTime();
        // 일반 파라미터 값 채우기
        if (sysCampusName == null) {
            sysCampusName = dietServiceV2.getCampusName(kakaoId);
        } else if (sysDate == null) {
            sysDate = dietServiceV2.getDay(time);
        } else if (sysPeriod == null) {
            sysPeriod = dietServiceV2.getPeriodOfDay(time);
        }

        // sysDate로 날짜 계산하기
        String dateTime = dietServiceV2.getCurrentDateTime(sysDate);
        String dietDescription = dietServiceV2.getDietDescription(sysCafeteriaName, campusId, sysPeriod, dateTime);
        // 식당 imgUrl 찾기
        String imgUrl = dietServiceV2.getImageUrl(campusId);
    }


}
