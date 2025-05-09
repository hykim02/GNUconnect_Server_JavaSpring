package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.diet.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/spring")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietService;
    private static final Logger logger = LoggerFactory.getLogger(DietController.class);

    @PostMapping("/v2/dish")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        String threadName = Thread.currentThread().getName();
        logger.info("[식단 요청] 스레드 이름: {}", threadName);

        return dietService.requestHandler(requestDto);
    }

}
