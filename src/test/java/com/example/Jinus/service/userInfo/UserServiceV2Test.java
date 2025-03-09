package com.example.Jinus.service.userInfo;

import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceV2Test {

    @Autowired
    @InjectMocks
    private UserServiceV2 userServiceV2;
    @MockBean
    private UserRepositoryV2 userRepositoryV2;

    @Test
    @DisplayName("등록된 사용자 campusId 확인")
    public void checkExistsUserCampusId() {
        // given
        String kakaoId = "test_user_1234";
        int campusId = 1;

        // when
        Mockito.when(userRepositoryV2.findCampusIdById(kakaoId)).thenReturn(Optional.of(campusId)); // Mocking
        int result = userServiceV2.getUserCampusId(kakaoId);

        // then - 존재하면 campusId 반환
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("등록되지 않은 사용자 확인")
    public void checkUserNotExists() {
        // given
        String kakaoId = "not_exist_user_1234";

        // when
        Mockito.when(userRepositoryV2.findCampusIdById(kakaoId)).thenReturn(Optional.empty()); // Mocking
        int result = userServiceV2.getUserCampusId(kakaoId);

        // then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("존재하는 사용자 학과id 찾기")
    public void checkExistsUserDepartmentId() {
        // given
        String kakaoId = "test_user_1234";
        int departmentId = 1;

        // when
        Mockito.when(userRepositoryV2.findDepartmentIdById(kakaoId)).thenReturn(Optional.of(departmentId));
        int result = userServiceV2.getUserDepartmentId(kakaoId);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 학과id 찾기")
    public void checkDoesNotExistUserDepartmentId() {
        // given
        String kakaoId = "not_exist_user_1234";

        // when
        Mockito.when(userRepositoryV2.findDepartmentIdById(kakaoId)).thenReturn(Optional.empty());
        int result = userServiceV2.getUserDepartmentId(kakaoId);

        // then
        assertThat(result).isEqualTo(-1);
    }


    @Test
    @DisplayName("사용자 학과 ID 캐싱 테스트")
    public void testUserCampusIdCaching() {
        // Given: 특정 userId에 대해 학과 ID 반환
        String userId = "user123";
        int campusId = 12;

        Mockito.when(userRepositoryV2.findCampusIdById(userId))
                .thenReturn(Optional.of(campusId));

        // When: 첫 번째 조회 (DB에서 가져옴)
        int firstCall = userServiceV2.getUserCampusId(userId);
        int secondCall = userServiceV2.getUserCampusId(userId); // 캐시에서 가져와야 함

        // Then: 두 번째 호출 시 DB 호출 없이 동일한 값이 반환되어야 함
        assertThat(firstCall).isEqualTo(12);  //
        assertThat(secondCall).isEqualTo(firstCall);

        // Verify: 한 번만 호출되어야 함
        Mockito.verify(userRepositoryV2, times(1)).findCampusIdById(userId);
    }
}
