package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.v2.cafeteria.DietServiceV2;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/spring")
@RequiredArgsConstructor
public class DietControllerV2 {
    private final DietServiceV2 dietServiceV2;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/v2/dish")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        return dietServiceV2.requestHandler(requestDto);
    }

}
