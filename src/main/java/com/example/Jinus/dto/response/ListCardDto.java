package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListCardDto {
    private HeaderDto header;
    private List<ListItemDto> items;
    private List<ButtonDto> buttons;

    public ListCardDto(HeaderDto header, List<ListItemDto> items, List<ButtonDto> buttons) {
        this.header = header;
        this.items = items;
        this.buttons = buttons;
    }

    public ListCardDto(HeaderDto header, List<ListItemDto> items) {
        this.header = header;
        this.items = items;
    }
}
