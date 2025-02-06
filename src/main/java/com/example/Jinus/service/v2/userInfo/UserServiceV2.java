package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceV2 {

    private final UserRepositoryV2 userRepositoryV2;

    public UserServiceV2(UserRepositoryV2 userRepositoryV2) {
        this.userRepositoryV2 = userRepositoryV2;
    }

    // 사용자 등록 여부 확인
    public int getUserCampusId(String userId) {
        Optional<Integer> campusId = userRepositoryV2.findCampusIdById(userId);
        // 존재한다면 campusId, 존재하지 않는다면 -1 반환
        return campusId.orElse(-1);
    }


}
