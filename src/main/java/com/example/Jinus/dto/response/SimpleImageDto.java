package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleImageDto {
    String imageUrl;
    String altText;

    public SimpleImageDto(String imageUrl, String altText) {
        this.imageUrl = imageUrl;
        this.altText = altText;
    }
}
