package com.example.Jinus.utility;

import com.example.Jinus.dto.response.*;

import java.util.ArrayList;
import java.util.List;

public class TextCardResponse {

    public static String textCardResponse(String title, List<ButtonDto> buttons) {
        List<ComponentDto> componentDtoList = new ArrayList<>();
        TextCardDto textCardDto = new TextCardDto(title, buttons);

        ComponentDto componentDto = new ComponentDto(textCardDto);
        componentDtoList.add(componentDto);

        TemplateDto templateDto = new TemplateDto(componentDtoList, null);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
