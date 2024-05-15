package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicCardDto {
    private String title;
    private String description;
    private ThumbnailDto thumbnail;

    public BasicCardDto(String title, String description, ThumbnailDto thumbnail) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }
}
