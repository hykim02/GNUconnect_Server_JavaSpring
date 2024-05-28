package com.example.Jinus.service.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.repository.cafeteria.CampusRepository;
import com.example.Jinus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CampusService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final CampusRepository campusRepository;

    @Autowired
    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    public List<CampusEntity> getCampusList() {
        return campusRepository.findCampus();
    }

    public String getCampusName(int campusId) {
        String campusName = campusRepository.findCampusNameById(campusId);

        return campusName;
    }

    public int getCampusId(String campusName) {
        int campusId = campusRepository.findCampusIdByName(campusName);

        return campusId;
    }

}
