package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record CarouselDto(String type,
                          List<CarouselItemDto> items) {

}
