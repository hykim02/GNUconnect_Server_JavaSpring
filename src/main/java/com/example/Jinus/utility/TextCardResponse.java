package com.example.Jinus.utility;

import com.example.Jinus.dto.response.*;

import java.util.ArrayList;
import java.util.List;

public class TextCardResponse {
    // 학과 카테고리가 존재하지 않는 경우 카테고리가 존재않다고 블록 리턴(예외처리)
    public String textCardResponse(String title) {
        List<ComponentDto> componentDtoList = new ArrayList<>();
        TextCardDto textCardDto = new TextCardDto(title);

        ComponentDto componentDto = new ComponentDto(textCardDto);
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }

    public static String textCardResponse(String title, List<ButtonDto> buttons) {
        List<ComponentDto> componentDtoList = new ArrayList<>();
        TextCardDto textCardDto = new TextCardDto(title, buttons);

        ComponentDto componentDto = new ComponentDto(textCardDto);
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
