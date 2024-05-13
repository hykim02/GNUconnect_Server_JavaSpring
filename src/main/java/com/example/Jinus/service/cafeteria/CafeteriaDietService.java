package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaDietRepository;
import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public class CafeteriaDietService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CafeteriaDietRepository cafeteriaDietRepository;

    @Autowired
    public CafeteriaDietService(CafeteriaDietRepository cafeteriaDietRepository) {
        this.cafeteriaDietRepository = cafeteriaDietRepository;
        logger.info("CafeteriaDietService 실행");
    }

    public List<String> getCafeteriaDiet(LocalDate date, String time, int cafeteriaId) {
        logger.info("getCafeteriaDiet 실행");

        List<String> cafeteriaMenu = cafeteriaDietRepository.findCategoryAndDiet(date, time, cafeteriaId);
        logger.info("cafeteriaMenu: {}", cafeteriaMenu);

        return cafeteriaMenu;
    }
}
