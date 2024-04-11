package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComponentDto {
    private CarouselDto carousel;

    public ComponentDto(CarouselDto carousel) {
        this.carousel = carousel;
    }
}
