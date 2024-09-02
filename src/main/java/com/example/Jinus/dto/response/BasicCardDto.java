package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BasicCardDto {
    private String title;
    private String description;
    private ThumbnailDto thumbnail;
    private List<ButtonDto> buttons;

    public BasicCardDto(String title, String description, ThumbnailDto thumbnail, List<ButtonDto> buttons) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.buttons = buttons;
    }
}
