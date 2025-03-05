package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.v2.cafeteria.DietServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spring")
@RequiredArgsConstructor
public class DishControllerV2 {
    private final DietServiceV2 dietServiceV2;

    @PostMapping("/dish/v2")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        return dietServiceV2.requestHandler(requestDto);
    }
}
