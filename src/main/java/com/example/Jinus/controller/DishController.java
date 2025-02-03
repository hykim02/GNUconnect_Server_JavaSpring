package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.CollegeService;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import com.example.Jinus.service.cafeteria.CafeteriaDietService;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.cafeteria.CampusService;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DishController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final CafeteriaService cafeteriaService;
    private final DepartmentService departmentService;
    private final CollegeService collegeService;
    private final CafeteriaDietService cafeteriaDietService;
    private final CampusService campusService;

    public DishController(
            CafeteriaService cafeteriaService,
            DepartmentService departmentService,
            CollegeService collegeService,
            CafeteriaDietService cafeteriaDietService, CampusService campusService) {
        this.cafeteriaService = cafeteriaService;
        this.departmentService = departmentService;
        this.collegeService = collegeService;
        this.cafeteriaDietService = cafeteriaDietService;
        this.campusService = campusService;
    }
    @PostMapping("/api/spring/dish")
    public String handleRequest(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId(); // 유저 id
        String rawDate = requestDto.getAction().getDetailParams().getSys_date().getOrigin(); // 날짜 (오늘, 내일)
        String campusName = (requestDto.getAction().getParams().getSys_campus_name() != null)
                        ? requestDto.getAction().getParams().getSys_campus_name()
                        : ""; // 캠퍼스 이름 (동의어 사용)
        String cafeteriaName = requestDto.getAction().getParams().getSys_cafeteria_name(); // 식당 이름 (동의어 사용)
        String rawPeriod = (requestDto.getAction().getDetailParams().getSys_time_period() != null)
                        ? requestDto.getAction().getDetailParams().getSys_time_period().getOrigin()
                        : ""; //

        // 유효성 검사
        if (!cafeteriaDietService.validateDate(rawDate)) {
            return SimpleTextResponse.simpleTextResponse("오늘과 내일의 식단만 조회할 수 있어!");
        }

        String dishInfo = cafeteriaDietService.processRequest(userId, rawDate, campusName, cafeteriaName, rawPeriod);
        return dishInfo;
    }
}
