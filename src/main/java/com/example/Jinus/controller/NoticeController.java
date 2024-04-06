package com.example.Jinus.controller;

import com.example.Jinus.dto.request.NoticeRequestDto;
import com.example.Jinus.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final UserService userService;
    private final CollegeService collegeService;
    private final DepartmentService departmentService;
    private final CategoryService categoryService;
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(
            UserService userService,
            CollegeService collegeService,
            DepartmentService departmentService,
            CategoryService categoryService,
            NoticeService noticeService
    ) {
        this.userService = userService;
        this.collegeService = collegeService;
        this.departmentService = departmentService;
        this.categoryService = categoryService;
        this.noticeService = noticeService;
    }


    @PostMapping("/department-notice")
    public String handleRequest(@RequestBody NoticeRequestDto noticeRequestDto) {
        String userId = noticeRequestDto.getUserRequest().getUser().getId();
        logger.info("handleRequest 실행");
        logger.info("userId {}", userId);

        List<Integer> categoryIdList = new ArrayList<>();
        List<String> categoryTypeList = new ArrayList<>();
        Map<Integer, Map<String, String>> noticeMap = new HashMap<>();

        int departmentId = userService.getDepartmentId(userId); // 학과 찾기
        int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
        String collegeEng = collegeService.getCollegeName(collegeId); // 영문명 찾기(테이블 조회 위함)

        logger.info("departmentId: {}", departmentId);
        logger.info("collegeId: {}", collegeId);
        logger.info("collegeEng: {}", collegeEng);

        Map<Integer, String> categories = categoryService.getCategory(departmentId, collegeEng); // 카테고리 찾기

        // 카테고리 id 추출
        categories.forEach((key, value) -> {
            // key와 value를 사용하여 작업 수행
            categoryIdList.add(key);
            categoryTypeList.add(value);
        });

        logger.info("categoryIdList: {}", categoryIdList);
        logger.info("categoryTypeList: {}", categoryTypeList);

        // 공지 가져오기
        for (int categoryId : categoryIdList) {
            noticeMap = noticeService.getNotice(departmentId, categoryId, collegeEng);
            logger.info("noticeMap:{}", noticeMap);
        }

        return userId;
    }
}
