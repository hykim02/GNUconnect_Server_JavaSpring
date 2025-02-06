package com.example.Jinus.service.cafeteria;

import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import com.example.Jinus.repository.v2.userInfo.UserRepositoryV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CampusServiceV2Test {

    @Autowired
    private CampusServiceV2 campusServiceV2;
    @MockBean
    private CampusRepositoryV2 campusRepositoryV2;

    @Test
    @DisplayName("사용자 캠퍼스 찾기")
    public void checkUserCampusName() {
        // given - 파라미터
        int campusId = 1;
        String campusName = "가좌캠퍼스";

        // when
        Mockito.when(campusRepositoryV2.findCampusNameById(campusId)).thenReturn(campusName); // Mocking
        String result = campusServiceV2.findUserCampusName(campusId);
        System.out.println("result:" + result);

        // then
        assertThat(result).isEqualTo(campusName);
    }
}
