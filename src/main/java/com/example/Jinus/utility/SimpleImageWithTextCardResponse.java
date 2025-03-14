package com.example.Jinus.utility;

import com.example.Jinus.dto.response.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleImageWithTextCardResponse {
    public static String simpleImageWithTextCardResponse(String imageUrl, String altText, String message, List<ButtonDto> buttons) {
        SimpleImageDto simpleImageDto = new SimpleImageDto(imageUrl, altText);
        TextCardDto simpleTextDto = new TextCardDto(message, buttons);

        List<ComponentDto> components = new ArrayList<>();
        ComponentDto componentDto = new ComponentDto(simpleImageDto);
        components.add(componentDto);
        componentDto = new ComponentDto(simpleTextDto);
        components.add(componentDto);

        TemplateDto templateDto = new TemplateDto(components, null);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
