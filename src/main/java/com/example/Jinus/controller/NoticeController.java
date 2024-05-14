package com.example.Jinus.controller;

import com.example.Jinus.component.ProcessMonitor;
import com.example.Jinus.dto.request.RequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.service.*;
import com.example.Jinus.service.notice.CategoryService;
import com.example.Jinus.service.notice.NoticeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.List;

@RestController
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final UserService userService;
    private final CollegeService collegeService;
    private final DepartmentService departmentService;
    private final CategoryService categoryService;
    private final NoticeService noticeService;
    private final ProcessMonitor processMonitor;


    @Autowired
    public NoticeController(
            UserService userService,
            CollegeService collegeService,
            DepartmentService departmentService,
            CategoryService categoryService,
            NoticeService noticeService, ProcessMonitor processMonitor
    ) {
        this.userService = userService;
        this.collegeService = collegeService;
        this.departmentService = departmentService;
        this.categoryService = categoryService;
        this.noticeService = noticeService;
        this.processMonitor = processMonitor;
    }

    @PostMapping("/department-notice")
    public String handleRequest(@RequestBody RequestDto requestDto) throws ParseException {
        logger.info("NoticeController 실행");
        logger.info("handleRequest 실행");
        // 사용자 요청을 감지하여 프로세스 종료 스케줄링을 취소
//        processMonitor.onRequestReceived();
//        processMonitor.startProcessShutdownTimer();
        return findUserId(requestDto);
    }

    // userId 존재 여부 확인
    public String findUserId(@RequestBody RequestDto requestDto) throws ParseException {
        logger.info("findUserId 실행");
        String userId = requestDto.getUserRequest().getUser().getId();
        int departmentId = userService.getDepartmentId(userId); // 학과 찾기(userId가 null인 경우 -1 리턴)

        // 학과 인증 메시지 리턴
        if (departmentId == -1) {
            logger.info("userId: null");
            return simpleTextResponse();
        } else {
            logger.info("userId: {}", userId);
            return findNotice(departmentId);
        }
    }

    // userId 존재하는 경우 공지 찾기
    public String findNotice(int departmentId) throws ParseException {
        logger.info("findNotice 실행");
        List<Integer> categoryIdList = new ArrayList<>();
        List<Map<String, String>> noticeList;
        Map<Integer, List<Map<String, String>>> noticeMap = new HashMap<>();

        int collegeId = departmentService.getCollegeId(departmentId); // 단과대학 찾기
        String departmentEng = departmentService.getDepartmentEng(departmentId); // 학과 영문명 찾기(url 생성 위함)
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
            noticeList = noticeService.getNotice(categoryId, collegeEng);
            noticeMap.put(categoryId, noticeList);
        }
        logger.info("noticeMap: {}", noticeMap);

        return noticeResponse(noticeMap, categories, departmentEng);
    }

    // userId 존재하지 않는 경우 학과인증 블록 리턴(예외처리)
    public String simpleTextResponse() {
        List<ComponentDto> componentDtoList = new ArrayList<>();
        List<ButtonDto> buttonList = new ArrayList<>();
        // 블록 버튼 생성
        ButtonDto buttonDto = new ButtonDto("학과 인증하기", "block", null,"6623de277e38b92310022cd8");
        buttonList.add(buttonDto);

        TextCardDto textCardDto = new TextCardDto("학과 인증을 진행해주세요.", buttonList);
        ComponentDto componentDto = new ComponentDto(textCardDto);
        componentDtoList.add(componentDto);
        TemplateDto templateDto = new TemplateDto(componentDtoList);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return toJsonResponse(responseDto);
    }

    // userId 존재하는 경우
    public String noticeResponse(Map<Integer, List<Map<String, String>>> noticeMap,
                                 Map<Integer, Map<String, String>> categories,
                                 String departmentEng) {
        // component type
        List<CarouselItemDto> carouselItems = new ArrayList<>(); // 카테고리 개수만큼 생성

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
                    String spltCreatedAt = createdAt.substring(0, 10); // 날짜 문자열 자르기
                    String nttSn = notice.get("ntt_sn");
                    LinkItemDto detailLink = new LinkItemDto(noticeDetailUrl(departmentEng, mi, bbsId, nttSn)); // 링크 객체 생성
                    ListItemDto noticeListItem = new ListItemDto(title, spltCreatedAt, detailLink);
                    listItems.add(noticeListItem);
                });
                logger.info("listItems: {}", listItems);

                List<ButtonDto> buttonDto = new ArrayList<>();
                // 공지 버튼 생성
                ButtonDto button = new ButtonDto("더보기", "webLink", noticeCategoryUrl(departmentEng, mi, bbsId), null);
                buttonDto.add(button); // 버튼 1개

                // 캐로셀 아이템
                CarouselItemDto carouselItem = new CarouselItemDto(header, listItems, buttonDto);
                carouselItems.add(carouselItem);
            }
        });

        List<ComponentDto> outputs = new ArrayList<>();

        // 캐로셀 컴포넌트
        CarouselDto carouselComponent = new CarouselDto("listCard", carouselItems);
        ComponentDto cardTypeDto = new ComponentDto(carouselComponent);
        outputs.add(cardTypeDto);

        TemplateDto template = new TemplateDto(outputs);
        ResponseDto responseDto = new ResponseDto("2.0", template);

        return toJsonResponse(responseDto);
    }

    // ObjectMapper를 사용하여 ResponseDto 객체를 JSON 문자열로 변환
    public String toJsonResponse(ResponseDto responseDto) {
        String jsonResponse;
        ObjectMapper objectMapper = new ObjectMapper();

        // null 값 무시 설정
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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

    // 공지 상세페이지 주소
    public String noticeDetailUrl(String departmentEng, String mi, String bbsId, String nttSn) {
        String baseUrl = "https://www.gnu.ac.kr/" + departmentEng + "/na/ntt/selectNttInfo.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId + "&nttSn=" + nttSn;
    }

    // 공지 카테고리 주소
    public String noticeCategoryUrl(String departmentEng, String mi, String bbsId) {
        String baseUrl = "https://www.gnu.ac.kr/" + departmentEng + "/na/ntt/selectNttList.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId;
    }
}
