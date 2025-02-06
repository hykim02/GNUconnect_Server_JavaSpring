package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ListCardDto {
    private HeaderDto header;
    private ArrayList<ListItemDto> items;

    public ListCardDto(HeaderDto header, ArrayList<ListItemDto> items) {
        this.header = header;
        this.items = items;
    }
}
