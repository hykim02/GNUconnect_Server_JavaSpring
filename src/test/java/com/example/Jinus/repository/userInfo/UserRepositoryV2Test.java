package com.example.Jinus.repository.userInfo;

import com.example.Jinus.entity.userInfo.UserEntity;
import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity("test1234", 1, 1);
        userRepositoryV2.save(userEntity);
    }

    @Autowired
    private UserRepositoryV2 userRepositoryV2;

    @Test
    @DisplayName("사용자 등록여부 확인")
    public void checkUserExists() {
        // Given - 예상 데이터
        String kakaoId = "test1234";

        // When - 사용자 campusId 조회
        Optional<Integer> result = userRepositoryV2.findCampusIdById(kakaoId);

        // Then - 해당 campusId가 반환되어야 한다
        assertThat(result.orElse(-1)).isEqualTo(1);
    }

    @Test
    @DisplayName("등록되지 않은 사용자 확인")
    public void checkUserNotExists() {
        // given - 존재하지 않는 사용자 키
        String kakaoId = "abcd";

        // When - 사용자 campusId 조회
        Optional<Integer> result = userRepositoryV2.findCampusIdById(kakaoId);

        // Then - 결과가 없으므로 Optional.empty()이어야 한다
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하는 사용자 학과id 찾기")
    public void checkUserDepartmentId() {
        // given - 존재하는 사용자 키
        String kakaoId = "test1234";

        // when
        Optional<Integer> result = userRepositoryV2.findDepartmentIdById(kakaoId);

        // then
        assertThat(result.orElse(-1)).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 학과id 찾기")
    public void checkUserDoNotHaveDepartmentId() {
        // given - 존재하지 않는 사용자 키
        String kakaoId = "abcd";

        // when
        Optional<Integer> result = userRepositoryV2.findDepartmentIdById(kakaoId);

        // then
        assertThat(result).isEmpty();
    }
}
