package com.example.Jinus.controller;

import com.example.Jinus.dto.request.ActionDto;
import com.example.Jinus.dto.request.DetailParamDto;
import com.example.Jinus.dto.request.UserRequestDto;
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

    @PostMapping("/cafeteria")
    public void handleRequest(@RequestBody ActionDto actionDto) {
        String id = actionDto.getId();
        logger.info("id: " + id);
        String name = actionDto.getName();
        logger.info("name: " + name);
        Map<String, DetailParamDto> detailParamDto = actionDto.getDetailParams();
        logger.info("detailParamDto: " + detailParamDto);
    }
}
