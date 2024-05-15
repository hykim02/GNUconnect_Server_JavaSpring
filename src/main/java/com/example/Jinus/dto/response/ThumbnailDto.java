package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbnailDto {
    private String imageUrl;

    public ThumbnailDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
