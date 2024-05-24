package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuickReplyDto {
    private String label; // 사용자에게 노출될 바로가기 응답의 표시
    private String action; // 바로가기 응답의 기능
    private String messageText; // 사용자 측으로 노출될 발화

    public QuickReplyDto(String label, String action, String messageText) {
        this.label = label;
        this.action = action;
        this.messageText = messageText;
    }
}
