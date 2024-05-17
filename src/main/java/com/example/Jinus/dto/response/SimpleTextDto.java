package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTextDto {
    String text;

    public SimpleTextDto(String text) {
        this.text = text;
    }
}
