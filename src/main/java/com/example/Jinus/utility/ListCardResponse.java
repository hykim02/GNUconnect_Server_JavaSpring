package com.example.Jinus.utility;

import com.example.Jinus.dto.response.*;

import java.util.ArrayList;
import java.util.List;

public class ListCardResponse {

    // ResponseDto 객체 생성
    public static ResponseDto mappingResponseDto(String msg, List<ListItemDto> listItems, List<ButtonDto> buttonDto) {
        // header 객체 생성
        HeaderDto header = new HeaderDto(msg);
        // ListCard 객체 생성
        ListCardDto listCardDto = new ListCardDto(header, listItems, buttonDto);
        // component 객체 생성
        ComponentDto componentDto = new ComponentDto(listCardDto);
        // template 객체 생성
        List<ComponentDto> outputs = new ArrayList<>();
        outputs.add(componentDto);
        TemplateDto templateDto = new TemplateDto(outputs);
        // response 객체 생성
        return new ResponseDto("2.0", templateDto);
    }
}
