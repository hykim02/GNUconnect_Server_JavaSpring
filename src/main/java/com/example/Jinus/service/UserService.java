package com.example.Jinus.service;

import com.example.Jinus.entity.UserEntity;
import com.example.Jinus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getDepartmentId(String userId) {
        // userId를 사용해 UserRepository에서 UserEntity 조회
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        // 사용자가 존재하는 경우
        if (userEntity != null) {
            return userEntity.getDepartmentId(); // 학과 id
        } else {
            return -1;
        }
    }
}
