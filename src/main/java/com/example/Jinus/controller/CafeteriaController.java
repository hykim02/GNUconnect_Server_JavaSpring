package com.example.Jinus.controller;

import com.example.Jinus.dto.request.ActionDto;
import com.example.Jinus.dto.request.DetailParamDto;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.request.UserRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CafeteriaController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

//    @PostMapping("/cafeteria")
//    public void handleRequest(@RequestBody String jsonPayload) {
//        // jsonPayload를 출력하여 확인
//        System.out.println("Received JSON Payload: " + jsonPayload);
//    }

    @PostMapping("/cafeteria")
    public void handleRequest(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        logger.info("userId: {}", userId);
    }

}
