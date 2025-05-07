package com.example.Jinus.controller;

import com.example.Jinus.service.cafeteria.CafeteriaService;
import com.example.Jinus.service.cafeteria.CampusService;
import com.example.Jinus.service.userInfo.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CafeteriaController.class)  // 해당 클래스만 테스트
public class CafeteriaControllerTest {

    @Autowired
    private MockMvc mockMvc;  // HTTP 요청을 모의
    @MockBean
    private CafeteriaService cafeteriaServiceV2;  // Service를 mock 처리
    @MockBean
    private CampusService campusServiceV2;
    @MockBean
    private UserService userServiceV2;

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
