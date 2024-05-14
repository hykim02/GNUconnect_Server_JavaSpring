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
        logger.info("dishCategory: {}", dishCategory);

        for (CafeteriaDietEntity dish : dishCategory) {
            logger.info("dish: {}", dish);
            logger.info("dish_category: {}", dish.getDishCategory());
            logger.info("dish_type: {}", dish.getDishType());
            logger.info("dish_name: {}", dish.getDishName());
        }

    }
}
