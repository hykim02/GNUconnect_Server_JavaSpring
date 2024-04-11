package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDto {
    private String version;
    private TemplateDto template;

    public ResponseDto(String version, TemplateDto template) {
        this.version = version;
        this.template = template;
    }
}
