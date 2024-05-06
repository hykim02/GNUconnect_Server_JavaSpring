package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateTextCardDto {
    private List<ComponentTextCardDto> outputs;

    public TemplateTextCardDto(List<ComponentTextCardDto> outputs) {
        this.outputs = outputs;
    }
}
