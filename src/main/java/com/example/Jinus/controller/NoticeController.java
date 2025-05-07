package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.notice.NoticeService;
import com.example.Jinus.service.userInfo.DepartmentService;
import com.example.Jinus.service.userInfo.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class NoticeController {

    private final NoticeService noticeServiceV2;
    private final DepartmentService departmentServiceV2;
    private final UserService userServiceV2;
    @Autowired
    private ObjectMapper objectMapper;

    // 학교 공지사항 조회
    @PostMapping("/v2/main-notice")
    public String getMainNotice() {
        int departmentId = 117; // 학교 공지사항 id
        String departmentEng = "main"; // 학과 영문명
        return noticeServiceV2.existUserReturnNotice(departmentEng, departmentId);
    }

    // 학과 공지사항 조회
    @PostMapping("/v2/department-notice")
    public String responseDepartmentNotice(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();

        int departmentId = userServiceV2.getUserDepartmentId(userId);
        // 학과 영문명 찾기
        String departmentEng = departmentServiceV2.getDepartmentEng(departmentId);
        // 사용자 학과 정보가 존재한다면
        if (departmentId != -1) {
            return noticeServiceV2.existUserReturnNotice(departmentEng, departmentId);
        } else {
            return noticeServiceV2.doesNotExistUserReturnBlock();
        }
    }
}
