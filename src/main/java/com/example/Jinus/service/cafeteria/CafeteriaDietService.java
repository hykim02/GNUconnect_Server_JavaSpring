package com.example.Jinus.service.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import com.example.Jinus.repository.cafeteria.CafeteriaDietRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("CafeteriaDietService 실행");
    }

    public void getCafeteriaDiet(LocalDate date, String time, int cafeteriaId) {
        logger.info("getCafeteriaDiet 실행");

        List<CafeteriaDietEntity> dishCategory = cafeteriaDietRepository.findCategoryOrType(date, time, cafeteriaId);
        HashMap<String, List<String>> CategoryMenuMap = new HashMap<>();
        List<String> menuList = new ArrayList<>(); // 메뉴 담을 리스트 생성 및 초기화
        List<String> categoryCheckList = new ArrayList<>(); // 카테고리 중복체크를 위한 리스트

        for (CafeteriaDietEntity dish : dishCategory) {

            logger.info("dish_category: {}", dish.getDishCategory());
            logger.info("dish_type: {}", dish.getDishType());
            logger.info("dish_name: {}", dish.getDishName());

            if (dish.getDishCategory() != null) {
                if (!categoryCheckList.contains(dish.getDishCategory())) {
                    categoryCheckList.add(dish.getDishCategory());
                }
                menuList.add(dish.getDishName());
                break;
            } else if (dish.getDishType() != null) {
                if (!categoryCheckList.contains(dish.getDishType())) {
                    categoryCheckList.add(dish.getDishType());
                }
                menuList.add(dish.getDishName());
            } else {
                categoryCheckList.add("menu");
            }
        }
    }

    public List<String> getDishName(List<CafeteriaDietEntity> dishCategory, List<String> menuList, String category) {
        for ( CafeteriaDietEntity dish : dishCategory) {
            if (dish.getDishCategory().equals(category)) {

            }
        }
    }
}
