package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HandleRequestDto {
    private final String kakaoId;
    private final String campusName;
    private final String day;
    private final String period;
    private final String cafeteriaName;

    public HandleRequestDto(String kakaoId, String campusName, String day, String period, String cafeteriaName) {
        this.kakaoId = kakaoId;
        this.campusName = campusName;
        this.day = day;
        this.period = period;
        this.cafeteriaName = cafeteriaName;
    }

}
