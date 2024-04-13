package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Any;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ListItemDto {
    private String title;
    private String description;
    private LinkItemDto link;
    private String imageUrl;
    private String action;
    private String blockId;
    private Map<String, Any> extra;
    private String messageText;

    public ListItemDto(String title, String description, LinkItemDto link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

}
