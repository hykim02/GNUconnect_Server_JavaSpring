package com.example.Jinus.dto.response;

import java.util.List;

public record ListCardDto(HeaderDto header,
                          List<ListItemDto> items,
                          List<ButtonDto> buttons) {

}
