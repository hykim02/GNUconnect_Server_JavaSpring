package com.example.Jinus.controller;

import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public String handleRequest(@RequestBody RequestDto noticeRequestDto) {
        return findNotice(noticeRequestDto);
    }

    public String findNotice(@RequestBody RequestDto noticeRequestDto) {
        String userId = noticeRequestDto.getUserRequest().getUser().getId();
        logger.info("handleRequest 실행");
        logger.info("userId: {}", userId);

        List<Integer> categoryIdList = new ArrayList<>();
        List<String> categoryTypeList = new ArrayList<>();
        List<Map<String, String>> noticeList;
        Map<Integer, List<Map<String, String>>> noticeMap = new HashMap<>();

        int departmentId = userService.getDepartmentId(userId); // 학과 찾기
        int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
        String collegeEng = collegeService.getCollegeName(collegeId); // 영문명 찾기(테이블 조회 위함)

        logger.info("departmentId: {}", departmentId); // 학과id
        logger.info("collegeId: {}", collegeId); // 단과대학id
        logger.info("collegeEng: {}", collegeEng); // 단과대학 영문명

        Map<Integer, Map<String, String>> categories = categoryService.getCategory(departmentId, collegeEng); // 카테고리 찾기

        // 카테고리 id 추출
        categories.forEach((key, value) -> {
            // key와 value를 사용하여 작업 수행
            categoryIdList.add(key);

            value.forEach((key2, value2) -> {
                if(key2.equals("category")) {
                    categoryTypeList.add(value2);
                }
            });
        });

        logger.info("categoryIdList: {}", categoryIdList);
        logger.info("categoryTypeList: {}", categoryTypeList);

        // 공지 가져오기
        for (int categoryId : categoryIdList) {
            noticeList = noticeService.getNotice(departmentId, categoryId, collegeEng);
            noticeMap.put(categoryId, noticeList);
        }
        logger.info("noticeMap: {}", noticeMap);

        String jsonResponse = noticeResponse(noticeMap, categoryTypeList);
        return jsonResponse;
    }


    public String noticeResponse(Map<Integer, List<Map<String, String>>> noticeMap,
                               List<String> categoryTypeList) {
        // response
        String version;
        ResponseDto.TemplateDTO template;
        List<ComponentDto> outputs = new ArrayList<>();

        // component type
        ComponentDto cardTypeDto;
        String carouselType;
        List<CarouselItemDto> carouselItems = new ArrayList<>(); // 카테고리 개수만큼 생성

        // carousel items
        CarouselItemDto.HeaderDto header;
        List<ListItemDto> listItems = new ArrayList<>(); // 공지 4개씩
        List<ButtonDto> buttonDto = new ArrayList<>();

        // listcard item
        String title;
        String description;
        ListItemDto.LinkItemDto link;

        // link
        String web;

        // button
        String label;
        String action;
        String webLinkUrl;

        // 첫 번째 카테고리
        CarouselItemDto.HeaderDto header1 = new CarouselItemDto.HeaderDto("공지 사항");
        // 공지 사항 내용
        ListItemDto.LinkItemDto linkItem = new ListItemDto.LinkItemDto("https://www.gnu.ac.kr/cse/na/ntt/selectNttInfo.do?mi=17093&bbsId=4753&nttSn=2229288");
        ListItemDto listItem = new ListItemDto("공지1", "내용1", linkItem);

        ListItemDto.LinkItemDto linkItem2 = new ListItemDto.LinkItemDto("https://www.gnu.ac.kr/cse/na/ntt/selectNttInfo.do?mi=17093&bbsId=4753&nttSn=2229288");
        ListItemDto listItem2 = new ListItemDto("공지2", "내용2", linkItem2);

        ListItemDto.LinkItemDto linkItem3 = new ListItemDto.LinkItemDto("https://www.gnu.ac.kr/cse/na/ntt/selectNttInfo.do?mi=17093&bbsId=4753&nttSn=2229288");
        ListItemDto listItem3 = new ListItemDto("공지3", "내용3", linkItem3);

        ListItemDto.LinkItemDto linkItem4 = new ListItemDto.LinkItemDto("https://www.gnu.ac.kr/cse/na/ntt/selectNttInfo.do?mi=17093&bbsId=4753&nttSn=2229288");
        ListItemDto listItem4 = new ListItemDto("공지4", "내용4", linkItem4);

        listItems.add(listItem);
        listItems.add(listItem2);
        listItems.add(listItem3);
        listItems.add(listItem4);

        // 첫 번째 카테고리 버튼 생성
        ButtonDto button = new ButtonDto("더보기", "webLink", "https://www.gnu.ac.kr/cse/na/ntt/selectNttList.do?mi=17093&bbsId=4753");
        buttonDto.add(button);

        // 캐로셀 아이템 1
        CarouselItemDto carouselItem = new CarouselItemDto(header1, listItems, buttonDto);
        carouselItems.add(carouselItem);

        // 캐로셀 컴포넌트
        ComponentDto.CarouselDto carouselComponent = new ComponentDto.CarouselDto("listCard", carouselItems);
        cardTypeDto = new ComponentDto(carouselComponent);
        outputs.add(cardTypeDto);
        template = new ResponseDto.TemplateDTO(outputs);
        ResponseDto responseDto = new ResponseDto("2.0", template);

        // ObjectMapper를 사용하여 ResponseDto 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;

        try {
            jsonResponse = objectMapper.writeValueAsString(responseDto);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 오류가 발생한 경우 처리
            e.printStackTrace();
            jsonResponse = "{}"; // 빈 JSON 응답 반환
        }

        // jsonResponse를 클라이언트로 보내는 코드 (예: HTTP 응답으로 보내거나, 채팅 메시지로 보내거나 등)
        System.out.println(jsonResponse);
        return jsonResponse;
    }
}
