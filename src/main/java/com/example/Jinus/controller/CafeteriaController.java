package com.example.Jinus.controller;

import com.example.Jinus.dto.request.ActionParamsDto;
import com.example.Jinus.dto.request.RequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        String params = requestDto.getAction().getParams().getSys_cafeteria_name();
        logger.info("params: {}", params);
    }

}
