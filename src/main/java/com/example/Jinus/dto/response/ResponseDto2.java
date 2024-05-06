package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto2 {
    private String version;
    private TemplateTextCardDto template;

    public ResponseDto2(String version, TemplateTextCardDto template) {
        this.version = version;
        this.template = template;
    }
}
