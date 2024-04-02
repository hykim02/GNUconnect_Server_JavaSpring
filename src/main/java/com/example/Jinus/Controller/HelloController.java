package com.example.Jinus.Controller;

import com.example.Jinus.Response;
import com.example.Jinus.Service.ItService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private ItService itService;

    @GetMapping("/test")
    public List<Response> getAllResponses() {
        return itService.getAllResponses();
    }
}
