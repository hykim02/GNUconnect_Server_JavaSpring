package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentDto {
    private CarouselDto carousel;
    private TextCardDto textCard;
    private BasicCardDto basicCard;
    private SimpleTextDto simpleText;
    private SimpleImageDto simpleImage;

    public ComponentDto(CarouselDto carousel) {
        this.carousel = carousel;
    }

    public ComponentDto(TextCardDto textCard) {
        this.textCard = textCard;
    }

    public ComponentDto(BasicCardDto basicCard) {
        this.basicCard = basicCard;
    }

    public ComponentDto(SimpleTextDto simpleText) {
        this.simpleText = simpleText;
    }

    public ComponentDto(SimpleImageDto simpleImage) { this.simpleImage = simpleImage; }
}
