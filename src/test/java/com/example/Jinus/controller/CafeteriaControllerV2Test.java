package com.example.Jinus.controller;

import com.example.Jinus.controller.v2.CafeteriaControllerV2;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CafeteriaControllerV2.class)  // 해당 클래스만 테스트
public class CafeteriaControllerV2Test {

    @Autowired
    private MockMvc mockMvc;  // HTTP 요청을 모의
    @MockBean
    private CafeteriaServiceV2 cafeteriaServiceV2;  // Service를 mock 처리
    @MockBean
    private CampusServiceV2 campusServiceV2;
    @MockBean
    private UserServiceV2 userServiceV2;

    @Test
    @DisplayName("식당 리스트 반환")
    public void checkCafeteriaListReturn() {
        // given
        String campusName = "가좌캠퍼스";
        List<Object[]> cafeteriaList = new ArrayList<>();
        Object[] row1 = {"아람관", "url"};
        Object[] row2 = {"교직원식당", "url"};
        cafeteriaList.add(row1);
        cafeteriaList.add(row2);

        // 예상 데이터



    }
}
