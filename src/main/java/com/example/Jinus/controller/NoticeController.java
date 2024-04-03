package com.example.Jinus.controller;

import com.example.Jinus.dto.request.NoticeRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @PostMapping("/department-notice")
    public String handleRequest(@RequestBody NoticeRequestDto noticeRequestDto) {
        String userId = noticeRequestDto.getUserRequest().getUser().getId();
        logger.info("notice request 실행");
        logger.info("userId {}", userId);

        return userId;
    }
}
