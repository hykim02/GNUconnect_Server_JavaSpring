package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicCardDto {
    private String title;
    private String description;
    private ThumbnailDto thumbnail;
}
