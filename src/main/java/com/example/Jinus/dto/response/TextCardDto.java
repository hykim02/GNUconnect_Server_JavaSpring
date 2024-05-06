package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextCardDto {
    String title;
    List<ButtonDto> buttons;

    public TextCardDto(String title, List<ButtonDto> buttons) {
        this.title = title;
        this.buttons = buttons;
    }
}
