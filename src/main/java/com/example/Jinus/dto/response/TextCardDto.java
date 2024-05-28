package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextCardDto {
    String title;
    String description;
    List<ButtonDto> buttons;

    public TextCardDto(String title) {
        this.title = title;
    }

    public TextCardDto(String title, List<ButtonDto> buttons) {
        this.title = title;
        this.buttons = buttons;
    }

    public TextCardDto(String title, String description, List<ButtonDto> buttons) {
        this.title = title;
        this.description = description;
        this.buttons = buttons;
    }
}
