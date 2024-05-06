package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentCarouselDto {
    private CarouselDto carousel;
//    private SimpleTextDto simpleTextDto;

    public ComponentCarouselDto(CarouselDto carousel) {
        this.carousel = carousel;
    }
//
//    public ComponentDto(SimpleTextDto simpleTextDto) {
//        this.simpleTextDto = simpleTextDto;
//    }
}
