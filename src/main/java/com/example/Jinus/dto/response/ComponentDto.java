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

    @Getter
    @Setter
    public static class CarouselDto {
        private String type;
        private List<CarouselItemDto> items;

        public CarouselDto(String type, List<CarouselItemDto> items) {
            this.type = type;
            this.items = items;
        }
    }
}
