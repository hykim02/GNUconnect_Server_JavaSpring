package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.service.v2.notice.CategoryServiceV2;
import com.example.Jinus.service.v2.notice.NoticeServiceV2;
import com.example.Jinus.service.v2.userInfo.DepartmentServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spring")
public class NoticeControllerV2 {

    private final NoticeServiceV2 noticeServiceV2;
    private final DepartmentServiceV2 departmentServiceV2;
    private final UserServiceV2 userServiceV2;

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
