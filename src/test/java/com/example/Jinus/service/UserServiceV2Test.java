package com.example.Jinus.service;

import com.example.Jinus.entity.userInfo.UserEntity;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class UserServiceV2Test {

    @Autowired
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
        System.out.println("result:" + result);

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
        System.out.println("result:" + result);

        // then
        assertThat(result).isEqualTo(-1);
    }
}
