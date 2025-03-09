package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceV2 {

    private final UserRepositoryV2 userRepositoryV2;

    public UserServiceV2(UserRepositoryV2 userRepositoryV2) {
        this.userRepositoryV2 = userRepositoryV2;
    }


    // 사용자 등록 여부 확인
    @Cacheable(
            value = "campusId",
            key = "#userId",
            unless = "#result == -1",
            cacheManager = "contentCacheManager"
    )
    public int getUserCampusId(String userId) {
        System.out.println("campusId 쿼리실행");
        Optional<Integer> campusId = userRepositoryV2.findCampusIdById(userId);
        // 존재한다면 campusId, 존재하지 않는다면 -1 반환
        return campusId.orElse(-1);
    }


    @Cacheable(
            value = "departmentId",
            key = "#userId",
            unless = "#result == -1",
            cacheManager = "contentCacheManager"
    )
    // 사용자 학과id 찾기
    public int getUserDepartmentId(String userId) {
        int departmentId = userRepositoryV2.findDepartmentIdById(userId).orElse(-1);

        // 부모 게시판 매핑 (해당 학과 ID가 없으면 기본값 그대로 유지)
        Map<Integer, Integer> parentDepartmentMap = Map.of(
                52, 51,
                53, 51,
                103, 102,
                104, 102,
                106, 105,
                107, 105
        );
        return parentDepartmentMap.getOrDefault(departmentId, departmentId);
    }

}
