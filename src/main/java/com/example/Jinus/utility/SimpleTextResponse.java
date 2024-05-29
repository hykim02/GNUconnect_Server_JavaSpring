package com.example.Jinus.utility;

import com.example.Jinus.dto.response.ComponentDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.dto.response.SimpleTextDto;
import com.example.Jinus.dto.response.TemplateDto;

import java.util.ArrayList;
import java.util.List;

public class SimpleTextResponse {
    // 학사일정 존재하지 않는 경우 예외처리
    public static String simpleTextResponse(String message) {
        SimpleTextDto simpleTextDto = new SimpleTextDto(message);
        ComponentDto componentDto = new ComponentDto(simpleTextDto);

        List<ComponentDto> components = new ArrayList<>();
        components.add(componentDto);

        TemplateDto templateDto = new TemplateDto(components);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
