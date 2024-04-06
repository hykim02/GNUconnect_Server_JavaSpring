package com.example.Jinus.service;

import com.example.Jinus.controller.NoticeController;
import com.example.Jinus.entity.UserEntity;
import com.example.Jinus.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserService 실행");
    }

    public int getDepartmentId(String userId) {
        logger.info("getDepartmentId 실행");

        // userId를 사용해 UserRepository에서 UserEntity 조회
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        logger.info("userEntity {}", userEntity);

        // 사용자가 존재하는 경우
        if (userEntity != null) {
            return userEntity.getDepartmentId(); // 학과 id
        } else {
            logger.error("UserService: user를 찾을 수 없습니다.");
            return -1;
        }
    }
}
