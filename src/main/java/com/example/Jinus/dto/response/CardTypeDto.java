package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardTypeDto {
    private CarouselDto carousel;

    public CardTypeDto(CarouselDto carousel) {
        this.carousel = carousel;
    }

    @Getter
    @Setter
    public static class CarouselDto {
        private String type;
        private List<CardItemDto> items;

        public CarouselDto(String type, List<CardItemDto> items) {
            this.type = type;
            this.items = items;
        }
    }
}
