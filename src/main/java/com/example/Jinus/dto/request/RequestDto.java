package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {
    private BotDto bot;
    private IntentDto intent;
    private ActionDto action;
    private UserRequestDto userRequest;
}
