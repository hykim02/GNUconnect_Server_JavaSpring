package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceV2 {

    private final UserRepositoryV2 userRepositoryV2;
    private final RedisTemplate<String, Object> redisTemplate;

    // 사용자 등록 여부 확인
    public int getUserCampusId(String userId) {
        // redis에서 데이터 찾음
        String key = "campusId:" + userId;
        // null값 처리 위해 Integer 사용
        Integer campusId = (Integer) redisTemplate.opsForValue().get(key);
        System.out.println("campusId:" + campusId);

        // redis에 데이터 없는 경우 db 조회
        if (campusId == null) {
            campusId = userRepositoryV2.findCampusIdById(userId).orElse(-1);
            // campusId가 -1이 아니면 캐시 저장
            if (campusId != -1) {
                redisTemplate.opsForValue().set(key, campusId, Duration.ofDays(1)); // 1일 TTL 설정
            }
        }
        return campusId;
    }

    // 사용자 학과 id 찾기
    public int getUserDepartmentId(String userId) {
        String key = "departmentId:" + userId;
        Integer departmentId = (Integer) redisTemplate.opsForValue().get(key);
        System.out.println("departmentId:" + departmentId);

        if (departmentId == null) {
            departmentId = userRepositoryV2.findDepartmentIdById(userId).orElse(-1);
            // campusId가 -1이 아니면 캐시 저장
            if (departmentId != -1) {
                redisTemplate.opsForValue().set(key, departmentId, Duration.ofDays(1)); // 1일 TTL 설정
            }
        }
        return getParentDepartmentId(departmentId);
    }

    // 부모 게시판 매핑 (해당 학과 ID가 없으면 기본값 그대로 유지)
    public int getParentDepartmentId(int childDepartmentId) {
        Map<Integer, Integer> parentDepartmentMap = Map.of(
                52, 51,
                53, 51,
                103, 102,
                104, 102,
                106, 105,
                107, 105
        );
        return parentDepartmentMap.getOrDefault(childDepartmentId, childDepartmentId);
    }


//    @Cacheable(
//            value = "campusId",
//            key = "#userId",
//            unless = "#result == -1",
//            cacheManager = "contentCacheManager"
//    )
//    public int getUserCampusId(String userId) {
//        Optional<Integer> campusId = userRepositoryV2.findCampusIdById(userId);
//        // 존재한다면 campusId, 존재하지 않는다면 -1 반환
//        return campusId.orElse(-1);
//    }


//    @Cacheable(
//            value = "departmentId",
//            key = "#userId",
//            unless = "#result == -1",
//            cacheManager = "contentCacheManager"
//    )
//    // 사용자 학과id 찾기
//    public int getUserDepartmentId(String userId) {
//        int departmentId = userRepositoryV2.findDepartmentIdById(userId).orElse(-1);
//
//        // 부모 게시판 매핑 (해당 학과 ID가 없으면 기본값 그대로 유지)
//        Map<Integer, Integer> parentDepartmentMap = Map.of(
//                52, 51,
//                53, 51,
//                103, 102,
//                104, 102,
//                106, 105,
//                107, 105
//        );
//        return parentDepartmentMap.getOrDefault(departmentId, departmentId);
//    }

}
