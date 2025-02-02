package com.example.Jinus.service.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import com.example.Jinus.repository.cafeteria.CafeteriaDietRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CafeteriaDietService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CafeteriaDietRepository cafeteriaDietRepository;

    @Autowired
    public CafeteriaDietService(CafeteriaDietRepository cafeteriaDietRepository) {
        this.cafeteriaDietRepository = cafeteriaDietRepository;
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


}