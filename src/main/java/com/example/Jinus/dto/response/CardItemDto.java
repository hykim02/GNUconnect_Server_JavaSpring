package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardItemDto {
    private HeaderDto header;
    private List<ListItemDto> items;
    private List<ButtonDto> buttons;

    public CardItemDto(HeaderDto header, List<ListItemDto> items, List<ButtonDto> buttons) {
        this.header = header;
        this.items = items;
        this.buttons = buttons;
    }

    @Getter
    @Setter
    public static class HeaderDto {
        private String title;

        public HeaderDto(String title) {
            this.title = title;
        }
    }
}
