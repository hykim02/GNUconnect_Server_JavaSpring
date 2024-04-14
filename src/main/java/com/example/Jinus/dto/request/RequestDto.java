package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private IntentDto intent;
    private UserRequestDto userRequest;
    private BotDto bot;
    private ActionDto action;
}
