package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateCarouselDto {
    private List<ComponentCarouselDto> outputs;

    public TemplateCarouselDto(List<ComponentCarouselDto> outputs) {
        this.outputs = outputs;
    }
}
