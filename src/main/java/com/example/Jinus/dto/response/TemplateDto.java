package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateDto {
    private List<ComponentDto> outputs;

    public TemplateDto(List<ComponentDto> outputs) {
        this.outputs = outputs;
    }
}
