package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListItemDto {
    private String title;
    private String description;
    private LinkItemDto link;

    public ListItemDto(String title, String description, LinkItemDto link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    @Getter
    @Setter
    public static class LinkItemDto {
        private String web;

        public LinkItemDto(String web) {
            this.web = web;
        }
    }
}
