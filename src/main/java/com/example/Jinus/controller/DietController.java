package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.diet.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring")
@RequiredArgsConstructor
public class DietController {
    private final DietService dietServiceV2;

    @PostMapping("/v2/dish")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        return dietServiceV2.requestHandler(requestDto);
    }

}
