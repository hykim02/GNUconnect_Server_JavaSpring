package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderDto {
    private String title;

    public HeaderDto(String title) {
        this.title = title;
    }
}
