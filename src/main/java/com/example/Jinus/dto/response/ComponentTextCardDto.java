package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentTextCardDto {
    private TextCardDto textCard;

    public ComponentTextCardDto(TextCardDto textCard) {
        this.textCard = textCard;
    }
}
