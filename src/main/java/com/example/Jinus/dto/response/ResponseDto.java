package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private String version;
    private TemplateCarouselDto template;

    public ResponseDto(String version, TemplateCarouselDto template) {
        this.version = version;
        this.template = template;
    }
}
