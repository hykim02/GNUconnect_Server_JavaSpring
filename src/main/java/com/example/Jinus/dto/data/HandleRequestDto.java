package com.example.Jinus.dto.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
public class HandleRequestDto {
    private final String kakaoId;
    private final String campusName;
    private final String day;
    private final String period;
    private final String cafeteriaName;
    private final Date dietDate;

    public HandleRequestDto(String kakaoId, String campusName, String day, String period, String cafeteriaName, Date dietDate) {
        this.kakaoId = kakaoId;
        this.campusName = campusName;
        this.day = day;
        this.period = period;
        this.cafeteriaName = cafeteriaName;
        this.dietDate = dietDate;
    }

}
