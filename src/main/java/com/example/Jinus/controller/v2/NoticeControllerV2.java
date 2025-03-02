package com.example.Jinus.controller.v2;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.service.notice.NoticeService;
import com.example.Jinus.service.v2.notice.NoticeCategoryServiceV2;
import com.example.Jinus.service.v2.notice.NoticeServiceV2;
import com.example.Jinus.service.v2.userInfo.DepartmentServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import com.example.Jinus.utility.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@RestController
public class NoticeControllerV2 {

    private final NoticeServiceV2 noticeServiceV2;
    private final NoticeCategoryServiceV2 noticeCategoryServiceV2;
    private final DepartmentServiceV2 departmentServiceV2;
    private final UserServiceV2 userServiceV2;

    public NoticeControllerV2(NoticeServiceV2 noticeServiceV2,
                              NoticeCategoryServiceV2 noticeCategoryServiceV2,
                              DepartmentServiceV2 departmentServiceV2,
                              UserServiceV2 userServiceV2) {
        this.noticeServiceV2 = noticeServiceV2;
        this.noticeCategoryServiceV2 = noticeCategoryServiceV2;
        this.departmentServiceV2 = departmentServiceV2;
        this.userServiceV2 = userServiceV2;
    }

    // 학과 공지사항 조회
    @PostMapping("/api/spring/department-notice/v2")
    public void responseDepartmentNotice(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userServiceV2.getUserDepartmentId(userId);
        // 사용자 학과 정보가 존재한다면
        if (departmentId != -1) {
            existUserReturnNotice(departmentId);
        }

    }

    // db에 학과정보가 있는 경우 -> 공지리스트 반환
    public String existUserReturnNotice(int departmentId) {
        // 학과 영문명 찾기
        String departmentEng = departmentServiceV2.getDepartmentEng(departmentId);
        // 학과 공지 카테고리들 가져오기
        List<NoticeCategoryEntity> categoryEntities = noticeCategoryServiceV2.getCategoryEntity(departmentId);

        // 카테고리 리스트 생성 (캐로셀 아이템 리스트)
        List<CarouselItemDto> categoryList = new ArrayList<>();
        for (NoticeCategoryEntity entity : categoryEntities) {
            // 해당 카테고리의 공지 ListCard 생성
            List<ListItemDto> noticeItemList = noticeServiceV2.getNoticeList(
                    entity.getId(), valueOf(entity.getMi()), valueOf(entity.getBbsId()), departmentEng);
            // ListCard 버튼 생성
            List<ButtonDto> buttonList = noticeServiceV2.makeButton(
                    departmentEng, valueOf(entity.getMi()), valueOf(entity.getBbsId()));
            // 카테고리 제목 header 객체 생성
            HeaderDto title = new HeaderDto(entity.getCategory());
            // 하나의 카테고리 아이템 생성
            CarouselItemDto carouselItem = new CarouselItemDto(title, noticeItemList, buttonList);
            categoryList.add(carouselItem);
        }
        CarouselDto carousel = new CarouselDto("listCard", categoryList);

        List<ComponentDto> componentList = new ArrayList<>();
        componentList.add(new ComponentDto(carousel));
        TemplateDto templateDto = new TemplateDto(componentList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);
        return JsonUtils.toJsonResponse(responseDto);
    }
}
