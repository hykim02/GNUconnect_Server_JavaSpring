package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public record BasicCardDto(String title,
                           String description,
                           ThumbnailDto thumbnail,
                           List<ButtonDto> buttons) {

}
