package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record CarouselItemDto(HeaderDto header,
                              List<ListItemDto> items,
                              List<ButtonDto> buttons) {

    public CarouselItemDto(HeaderDto header, List<ListItemDto> items) {
        this(header, items, null);
    }
}
