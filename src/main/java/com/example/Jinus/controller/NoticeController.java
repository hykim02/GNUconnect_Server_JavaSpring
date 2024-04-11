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

import java.text.ParseException;
import java.util.*;

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
    public String handleRequest(@RequestBody RequestDto noticeRequestDto) throws ParseException {
        return findNotice(noticeRequestDto);
    }

    public String findNotice(@RequestBody RequestDto noticeRequestDto) throws ParseException {
        String userId = noticeRequestDto.getUserRequest().getUser().getId();
        logger.info("handleRequest 실행");
        logger.info("userId: {}", userId);

        List<Integer> categoryIdList = new ArrayList<>();
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
        });

        logger.info("categoryIdList: {}", categoryIdList);
        logger.info("categories: {}", categories);

        // 공지 가져오기
        for (int categoryId : categoryIdList) {
            noticeList = noticeService.getNotice(departmentId, categoryId, collegeEng);
            noticeMap.put(categoryId, noticeList);
        }
        logger.info("noticeMap: {}", noticeMap);
        return noticeResponse(noticeMap, categories);
    }


    public String noticeResponse(Map<Integer, List<Map<String, String>>> noticeMap,
                                 Map<Integer, Map<String, String>> categories) {
        // component type
        List<CarouselItemDto> carouselItems = new ArrayList<>(); // 카테고리 개수만큼 생성
        String jsonResponse;

        logger.info("categories.forEach문 시작");
        // 카테고리 생성
        categories.forEach((categoryId, type) -> {
            // 카테고리 제목 생성
            logger.info("categoryId: {}", categoryId);
            logger.info("type: {}", type);
            String categoryType = type.get("category");
            String mi = type.get("mi");
            String bbsId = type.get("bbs_id");
            HeaderDto header = new HeaderDto(categoryType);

            logger.info("if문 시작");
            // 동일한 카테고리 id가 존재한다면
            if(noticeMap.containsKey(categoryId)) {
                List<Map<String, String>> noticeList = noticeMap.get(categoryId);
                logger.info("noticeList: {}", noticeList);
                List<ListItemDto> listItems = new ArrayList<>(); // 공지 4개씩
                // 공지 리스트 생성
                noticeList.stream().limit(4).forEach(notice -> {
                    logger.info("notice: {}", notice);
                    String title = notice.get("title");
                    String createdAt = notice.get("created_at");
                    String nttSn = notice.get("ntt_sn");
                    LinkItemDto detailLink = new LinkItemDto(noticeDetailUrl(mi, bbsId, nttSn)); // 링크 객체 생성
                    ListItemDto noticeListItem = new ListItemDto(title, createdAt, detailLink);
                    listItems.add(noticeListItem);
                });
                logger.info("listItems: {}", listItems);

                List<ButtonDto> buttonDto = new ArrayList<>();
                // 공지 버튼 생성
                ButtonDto button = new ButtonDto("더보기", "webLink", noticeCategoryUrl(mi, bbsId));
                buttonDto.add(button); // 버튼 1개

                // 캐로셀 아이템
                CarouselItemDto carouselItem = new CarouselItemDto(header, listItems, buttonDto);
                carouselItems.add(carouselItem);
            }
            logger.info("if문 끝");
        });
        logger.info("categories.forEach문 끝");

        List<ComponentDto> outputs = new ArrayList<>();

        // 캐로셀 컴포넌트
        CarouselDto carouselComponent = new CarouselDto("listCard", carouselItems);
        ComponentDto cardTypeDto = new ComponentDto(carouselComponent);
        outputs.add(cardTypeDto);

        TemplateDto template = new TemplateDto(outputs);
        ResponseDto responseDto = new ResponseDto("2.0", template);

        // ObjectMapper를 사용하여 ResponseDto 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            jsonResponse = objectMapper.writeValueAsString(responseDto);
        } catch (JsonProcessingException e) {
            // JSON 변환 중 오류가 발생한 경우 처리
            e.printStackTrace();
            jsonResponse = "{}"; // 빈 JSON 응답 반환(오류 메시지 출력하기)
        }

        // jsonResponse를 클라이언트로 보내는 코드
        System.out.println(jsonResponse);
        return jsonResponse;
    }

    public String noticeDetailUrl(String mi, String bbsId, String nttSn) {
        String baseUrl = "https://www.gnu.ac.kr/cse/na/ntt/selectNttInfo.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId + "&nttSn=" + nttSn;
    }

    public String noticeCategoryUrl(String mi, String bbsId) {
        String baseUrl = "https://www.gnu.ac.kr/cse/na/ntt/selectNttList.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId;
    }
}
