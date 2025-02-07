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
    private Map<String, Object> extra;
    private String messageText;

    public ListItemDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ListItemDto(String title, String description, LinkItemDto link) {
        this(title, description);
        this.link = link;
    }

    public ListItemDto(String title, String description, String imageUrl, String action, String messageText) {
        this(title, description);
        this.imageUrl = imageUrl;
        this.action = action;
        this.messageText = messageText;
    }

    public ListItemDto(String title, String description, String imageUrl, String action, String blockId, Map<String, Object> extra) {
        this(title, description);
        this.imageUrl = imageUrl;
        this.action = action;
        this.blockId = blockId;
        this.extra = extra;
    }

    public ListItemDto(String title, String imageUrl, String action, String blockId, Map<String, Object> extra) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.action = action;
        this.blockId = blockId;
        this.extra = extra;
    }
}
