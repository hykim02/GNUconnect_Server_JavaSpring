package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

public record QuickReplyDto(String label,
                           String action,
                           String messageText) {

}
