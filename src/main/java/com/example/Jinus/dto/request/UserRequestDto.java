package com.example.Jinus.dto.request;

// 학과 공지를 요청하는 request json

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String timezone;
    private UserParamsDto params;
    private BlockDto block;
    private String utterance;
    private String lang;
    private UserDto user;
}

