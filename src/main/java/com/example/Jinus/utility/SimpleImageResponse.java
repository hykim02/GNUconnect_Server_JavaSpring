package com.example.Jinus.utility;

import com.example.Jinus.dto.response.*;

import java.util.ArrayList;
import java.util.List;

public class SimpleImageResponse {
    public static String simpleImageResponse(String imageUrl, String altText) {
        SimpleImageDto simpleImageDto = new SimpleImageDto(imageUrl, altText);
        ComponentDto componentDto = new ComponentDto(simpleImageDto);

        List<ComponentDto> components = new ArrayList<>();
        components.add(componentDto);

        TemplateDto templateDto = new TemplateDto(components, null);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
