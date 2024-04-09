package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ButtonDto {
    private String label;
    private String action;
    private String webLinkUrl;

    public ButtonDto(String label, String action, String webLinkUrl) {
        this.label = label;
        this.action = action;
        this.webLinkUrl = webLinkUrl;
    }
}
