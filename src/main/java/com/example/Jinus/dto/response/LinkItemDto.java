package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkItemDto {
    private String web;

    public LinkItemDto(String web) {
        this.web = web;
    }
}
