package com.example.Jinus.service.cafeteria;

import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import com.example.Jinus.repository.cafeteria.CafeteriaDietRepository;
import com.example.Jinus.service.UserService;
import com.example.Jinus.utility.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CafeteriaDietService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CafeteriaDietRepository cafeteriaDietRepository;
    private final UserService userService;
    private final CafeteriaService cafeteriaService;
    private final CampusService campusService;

    @Autowired
    public CafeteriaDietService(CafeteriaDietRepository cafeteriaDietRepository,
                                UserService userService,
                                CafeteriaService cafeteriaService,
                                CampusService campusService) {
        this.cafeteriaDietRepository = cafeteriaDietRepository;
        this.userService = userService;
        this.cafeteriaService = cafeteriaService;
        this.campusService = campusService;
    }

    public String processRequest(String userId,
                                 String rawDate,
                                 String campusName,
                                 String cafeteriaName,
                                 String rawPeriod) {

        // 데이터 전처리
        String date = getCurrentDate(rawDate);
        int userCampusId = userService.getUserCampusId(userId);
        userCampusId = (userCampusId == -1) ? 1 : userCampusId;
        campusName = (campusName == null) ? campusService.getCampusName(userCampusId) : campusName;
        String period = (rawPeriod.isEmpty()) ? getPeriodOfDay() : rawPeriod;

        int cafeteriaId = cafeteriaService.getCafeteriaIdByCampusId(cafeteriaName, userCampusId);

        HashMap<String, List<String>> categoryMenuMap = getCafeteriaDiet(LocalDate.parse(date), period, cafeteriaId);

        return responseMapping(cafeteriaId, categoryMenuMap,
                        campusName, rawDate, cafeteriaName, period, date);
    }

    public boolean validateDate(String date) {
        return "오늘".equals(date) || "내일".equals(date) || "낼".equals(date);
    }

    // 시간 범위에 따라 오늘, 내일 판별하는 함수
    public static String getDay(String currentTime) {
        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("18:59:00"))) {
            return "오늘";
        } else {
            return "내일";
        }
    }

    // 시간 범위에 따라 아침, 점심, 저녁을 판별하는 함수
    public static String getPeriodOfDay() {
        String currentTime = getCurrentTime(); // 16:43:12
        String currentDay = getDay(currentTime); // 오늘, 내일

        LocalTime time = LocalTime.parse(currentTime);

        if (time.isAfter(LocalTime.parse("00:00:00")) && time.isBefore(LocalTime.parse("09:30:00"))) {
            return "아침";
        } else if (time.isAfter(LocalTime.parse("09:30:00")) && time.isBefore(LocalTime.parse("13:30:00"))) {
            return "점심";
        } else if (time.isAfter(LocalTime.parse("13:30:00")) && time.isBefore(LocalTime.parse("19:00:00"))) {
            return "저녁";
        } else {
            return "아침";
        }
    }

    // 현재 시간 출력 함수
    public static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String[] dateTimeParts = currentDateTime.toString().split("T");

        String timePart = dateTimeParts[1]; // 시간 부분
        String[] timeSplit = timePart.split("\\.");

        return timeSplit[0];
    }

    // 조회할 날짜 찾는 함수
    public static String getCurrentDate(String currentDay) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDate currentDate = currentDateTime.toLocalDate(); // 현재 날짜

        if (currentDay.equals("오늘")) {
            return currentDate.toString();
        } else { // 내일
            LocalDate tomorrowDate = currentDate.plusDays(1);
            return tomorrowDate.toString();
        }
    }

    // 식단 메뉴 블록 내용 정리
    public static String handleCafeteriaDiet(HashMap<String, List<String>> categoryMenuMap, String period, String currentDate) {
        String menuDescription = currentDate + " " + period + " 식단\n\n";

        if (categoryMenuMap.isEmpty()) { // 메뉴가 존재하지 않는다면
            return menuDescription + "메뉴가 존재하지 않습니다.";
        } else { // 메뉴가 존재한다면
            for (Map.Entry<String, List<String>> entry : categoryMenuMap.entrySet()) {
                String categoryName = entry.getKey();
                List<String> cafeteriaDishes = entry.getValue();
                menuDescription = joinAllMenus(cafeteriaDishes, categoryName, menuDescription);
            }
            return menuDescription;
        }
    }

    public static String joinAllMenus(List<String> cafeteriaDishes, String categoryName, String menuDescription) {
        String coveredCategory = "[" + categoryName + "]"; // 카테고리 괄호로 감싸기
        String joinedMenu = String.join("\n", cafeteriaDishes); // 메뉴들 컴마로 연결

        return menuDescription + coveredCategory + "\n" + joinedMenu + "\n\n";
    }

    @Cacheable(value = "cafeteriaDietCache", key = "#date + '-' + #time + '-' + #cafeteriaId", cacheManager = "contentCacheManager")
    public HashMap<String, List<String>> getCafeteriaDiet(LocalDate date, String time, int cafeteriaId) {

        List<CafeteriaDietEntity> dishCategory = cafeteriaDietRepository.findCategoryOrType(date, time, cafeteriaId);
        HashMap<String, List<String>> categoryMenuMap = new HashMap<>();
        List<String> categoryCheckList = new ArrayList<>(); // 카테고리 중복체크를 위한 리스트

        for (CafeteriaDietEntity dish : dishCategory) {
            if (dish.getDishCategory() != null) {
                if (!categoryCheckList.contains(dish.getDishCategory())) {
                    categoryCheckList.add(dish.getDishCategory());
                    List<String> menuList = new ArrayList<>(); // 메뉴 담을 리스트 생성 및 초기화
                    // 식단 가져오기
                    menuList = getDishNameByCategory(dishCategory, menuList, dish.getDishCategory());
                    // 카테고리 & 식단 매핑
                    categoryMenuMap.put(dish.getDishCategory(), menuList);
                }
            } else if (dish.getDishType() != null) {
                if (!categoryCheckList.contains(dish.getDishType())) {
                    categoryCheckList.add(dish.getDishType());
                    List<String> menuList = new ArrayList<>(); // 메뉴 담을 리스트 생성 및 초기화
                    // 식단 가져오기
                    menuList = getDishNameByType(dishCategory, menuList, dish.getDishType());
                    // 타입 & 식단 매핑
                    categoryMenuMap.put(dish.getDishType(), menuList);
                }
            } else {
                if (!categoryCheckList.contains("메뉴")) {
                    categoryCheckList.add("메뉴");
                    List<String> menuList = new ArrayList<>(); // 메뉴 담을 리스트 생성 및 초기화
                    // 식단 가져오기
                    menuList = getDishNameByExtra(dishCategory, menuList);
                    // 식단 매핑
                    categoryMenuMap.put("메뉴", menuList);
                }
            }
        }
        return categoryMenuMap;
    }

    // 카테고리별 식단 리스트 반환 (카테고리인 경우)
    public List<String> getDishNameByCategory(List<CafeteriaDietEntity> dishCategory, List<String> menuList, String category) {

        for (CafeteriaDietEntity dish : dishCategory) {
            if (dish.getDishCategory() != null && dish.getDishCategory().equals(category)) {
                menuList.add(dish.getDishName());
            } else {
                logger.debug("해당 카테고리의 메뉴가 없습니다.");
            }
        }
        return menuList;
    }

    // 카테고리별 식단 리스트 반환 (타입인 경우)
    public List<String> getDishNameByType(List<CafeteriaDietEntity> dishCategory, List<String> menuList, String type) {

        for ( CafeteriaDietEntity dish : dishCategory) {
            if (dish.getDishType().equals(type)) {
                menuList.add(dish.getDishName());
            } else {
                logger.debug("해당 타입의 메뉴가 없습니다.");
            }
        }
        return menuList;
    }

    // 카테고리별 식단 리스트 반환 (나머지 경우)
    public List<String> getDishNameByExtra(List<CafeteriaDietEntity> dishCategory, List<String> menuList) {

        for ( CafeteriaDietEntity dish : dishCategory) {
            if (dish.getDishCategory() == null && dish.getDishType() == null) {
                menuList.add(dish.getDishName());
            }  else {
                logger.debug("해당 메뉴가 없습니다.");
            }
        }
        return menuList;
    }

    // 식단 return 블록 생성
    public String responseMapping(int cafeteriaId, HashMap<String, List<String>> categoryMenuMap,
                                  String campus, String day, String originCafeteriaName, String period, String currentDate) {
        String url = cafeteriaService.getCampusThumnail(cafeteriaId);
        ThumbnailDto thumbnailDto = new ThumbnailDto(url);

        String title = "\uD83C\uDF71" + " " + originCafeteriaName + "(" + campus.substring(0, 2) + ")" + " 메뉴";
        String description = handleCafeteriaDiet(categoryMenuMap, period, currentDate);

        List<ButtonDto> buttons = new ArrayList<>();
        Map<String, Object> extra = new HashMap<>();
        extra.put("cafeteriaId", cafeteriaId);
        extra.put("date", currentDate);
        extra.put("time", period);
        ButtonDto buttonDto = new ButtonDto("공유하기", "share");
        buttons.add(buttonDto);

        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnailDto, buttons);
        ComponentDto componentDto = new ComponentDto(basicCardDto);

        List<ComponentDto> componentDtoList = new ArrayList<>();
        componentDtoList.add(componentDto);

        List<QuickReplyDto> quickReplies = new ArrayList<>();
        if (period.equals("아침")) {
            quickReplies.add(new QuickReplyDto("점심", "message", campus+" "+originCafeteriaName+" "+day+" 점심 메뉴"));
            quickReplies.add(new QuickReplyDto("저녁", "message", campus+" "+originCafeteriaName+" "+day+" 저녁 메뉴"));
        } else if (period.equals("점심")) {
            quickReplies.add(new QuickReplyDto("아침", "message", campus+" "+originCafeteriaName+" "+day+" 아침 메뉴"));
            quickReplies.add(new QuickReplyDto("저녁", "message", campus+" "+originCafeteriaName+" "+day+" 저녁 메뉴"));
        } else {
            quickReplies.add(new QuickReplyDto("아침", "message", campus+" "+originCafeteriaName+" "+day+" 아침 메뉴"));
            quickReplies.add(new QuickReplyDto("점심", "message", campus+" "+originCafeteriaName+" "+day+" 점심 메뉴"));
        }

        TemplateDto templateDto = new TemplateDto(componentDtoList, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }
}