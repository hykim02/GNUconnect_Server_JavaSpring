package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.repository.cafeteria.CampusRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampusService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CampusRepository campusRepository;

    @Autowired
    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
        logger.info("CampusService 실행");
    }

    public String getCampusName(int campusId) {
        logger.info("getCampusName 실행");

        String campusName = campusRepository.findCampusNameById(campusId);
        logger.info("campusName: {}", campusName);
        return campusName;
    }

}
