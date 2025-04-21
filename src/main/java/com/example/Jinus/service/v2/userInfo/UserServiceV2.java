package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceV2 {

    private final UserRepositoryV2 userRepositoryV2;

    public int getUserCampusId(String userId) {
        Optional<Integer> campusId = userRepositoryV2.findCampusIdById(userId);
        // 존재한다면 campusId, 존재하지 않는다면 -1 반환
        return campusId.orElse(-1);
    }

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

    // hikariCP test
    @Transactional
    public void selectCampusIdTest(String userId) throws InterruptedException {
        log.info("selectCampusIdTest 시작 - userId: {}", userId);
        Thread.sleep(2500); // 커넥션을 5초간 점유 (풀 점유 시 타임아웃 유도)
        userRepositoryV2.findCampusIdById(userId);
        log.info("selectCampusIdTest 종료 - userId: {}", userId);
    }
}
