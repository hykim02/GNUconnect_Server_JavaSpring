package com.example.Jinus.repository;

import com.example.Jinus.entity.userInfo.UserEntity;
import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class UserRepositoryV2Test {

    @Autowired
    private UserRepositoryV2 userRepositoryV2;

    @Test
    @DisplayName("사용자 등록여부 확인")
    public void checkUserExists() {
        // Given - 테스트 데이터 저장
        String kakaoId = "test_user_1234";
        int campusId = 1; //
        int departmentId = 1;

        UserEntity user = new UserEntity(kakaoId, campusId, departmentId); // 객체 생성
        userRepositoryV2.save(user); // DB에 사용자 정보 저장

        // When - 사용자 campusId 조회
        Optional<Integer> result = userRepositoryV2.findCampusIdById(kakaoId);
        System.out.println("result:" + result.get());

        // Then - 해당 campusId가 반환되어야 한다
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(1);
    }

    @Test
    @DisplayName("등록되지 않은 사용자 확인")
    public void checkUserNotExists() {
        // given - 존재하지 않는 사용자 키
        String kakaoId = "abcd";

        // When - 사용자 campusId 조회
        Optional<Integer> result = userRepositoryV2.findCampusIdById(kakaoId);
        System.out.println("result:" +result);

        // Then - 결과가 없으므로 Optional.empty()이어야 한다
        assertThat(result).isEmpty();
    }
}
