package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.service.userInfo.CollegeService;
import com.example.Jinus.service.userInfo.DepartmentService;
import com.example.Jinus.service.userInfo.UserService;
import com.example.Jinus.service.cafeteria.CafeteriaDietService;
import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.cafeteria.CampusService;
import com.example.Jinus.utility.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CafeteriaController {
    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final CafeteriaService cafeteriaService;
    private final UserService userService;
    private final DepartmentService departmentService;
    private final CollegeService collegeService;
    private final CampusService campusService;

    public CafeteriaController(
            CafeteriaService cafeteriaService,
            UserService userService,
            DepartmentService departmentService,
            CollegeService collegeService,
            CafeteriaDietService cafeteriaDietService, CampusService campusService) {
        this.cafeteriaService = cafeteriaService;
        this.userService = userService;
        this.departmentService = departmentService;
        this.collegeService = collegeService;
        this.campusService = campusService;
    }

    @PostMapping("/api/spring/cafeteria")
    public String getCafeteria(@RequestBody RequestDto requestDto) {
        String userId = requestDto.getUserRequest().getUser().getId();
        int campusId = userService.getUserCampusId(userId);
        int userWantedCampusId = requestDto.getAction().getClientExtra().getSys_campus_id();
        // 더보기를 누른 경우
        if (userWantedCampusId == -1) {
            return campusResponse(campusService.getCampusList());
        }
        // 유저 인증 X
        if (campusId == -1) {
            // 원하는 캠퍼스가 있는 경우
            if (userWantedCampusId > 0) {
                String campusName = campusService.getCampusName(userWantedCampusId);
                return cafeteriaResponse(cafeteriaService.getCafeteriaList(userWantedCampusId), campusName);
            }
            // 원하는 캠퍼스가 없는 경우
            else {
                return campusResponse(campusService.getCampusList());
            }
        }

        // 유저 인증 O
        else {
            // 원하는 캠퍼스가 있는 경우
            if (userWantedCampusId > 0) {
                String campusName = campusService.getCampusName(userWantedCampusId);
                return cafeteriaResponse(cafeteriaService.getCafeteriaList(userWantedCampusId), campusName);
            }
            // 원하는 캠퍼스가 없는 경우
            else {
                String campusName = campusService.getCampusName(campusId);
                return cafeteriaResponse(cafeteriaService.getCafeteriaList(campusId), campusName);

            }
        }
    }

    public String cafeteriaResponse(List<Object[]> cafeteriaList, String campusName) {
        // cafeteriaList 정렬
        cafeteriaList.sort((o1, o2) -> {
            String cafeteriaName1 = (String) o1[0];
            String cafeteriaName2 = (String) o2[0];
            return cafeteriaName1.compareTo(cafeteriaName2);
        });

        HeaderDto header = new HeaderDto("어떤 교내 식당 정보가 알고싶어 ?");
        List<ListItemDto> listItems = new ArrayList<>();

        for (Object[] cafeteria : cafeteriaList) {
            String cafeteriaName = (String) cafeteria[0];
            String imageUrl = (String) cafeteria[1];

            String cafeteriaMessage = campusName + " " + cafeteriaName;
            ListItemDto listItem = new ListItemDto(cafeteriaName, campusName, imageUrl, "message", cafeteriaMessage);
            listItems.add(listItem);
        }

        List<ButtonDto> buttonDto = new ArrayList<>();
        Map<String, Object> extra = new HashMap<>();
        extra.put("sys_campus_id", -1);
        ButtonDto button = new ButtonDto("더보기", "block", "", "66067167cdd882158c759fc2", extra);
        buttonDto.add(button);

        CarouselItemDto carouselItem = new CarouselItemDto(header, listItems, buttonDto);

        List<ComponentDto> outputs = new ArrayList<>();

        // 캐로셀 컴포넌트
        CarouselDto carouselComponent = new CarouselDto("listCard", List.of(carouselItem));
        ComponentDto cardTypeDto = new ComponentDto(carouselComponent);
        outputs.add(cardTypeDto);

        TemplateDto template = new TemplateDto(outputs);
        ResponseDto responseDto = new ResponseDto("2.0", template);

        return JsonUtils.toJsonResponse(responseDto);
    }

    public String campusResponse(List<CampusEntity> campusList) {
        HeaderDto header = new HeaderDto("어떤 캠퍼스 식당 정보가 궁금해 ?");
        List<ListItemDto> listItems = new ArrayList<>();

        for (CampusEntity campus : campusList) {
            String campusName = campus.getCampusNameKo();
            String imageUrl = campus.getThumbnailUrl();

            Map<String, Object> extra = new HashMap<>();
            extra.put("sys_campus_id", campus.getId());

            ListItemDto listItem = new ListItemDto(campusName, "", imageUrl, "block", "66067167cdd882158c759fc2", extra);
            listItems.add(listItem);
        }

        CarouselItemDto carouselItem = new CarouselItemDto(header, listItems);
        List<ComponentDto> outputs = new ArrayList<>();

        // 캐로셀 컴포넌트
        CarouselDto carouselComponent = new CarouselDto("listCard", List.of(carouselItem));
        ComponentDto cardTypeDto = new ComponentDto(carouselComponent);
        outputs.add(cardTypeDto);

        TemplateDto template = new TemplateDto(outputs);
        ResponseDto responseDto = new ResponseDto("2.0", template);

        return JsonUtils.toJsonResponse(responseDto);
    }
}
