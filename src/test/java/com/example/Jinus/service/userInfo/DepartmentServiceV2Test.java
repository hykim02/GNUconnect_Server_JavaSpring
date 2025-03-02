package com.example.Jinus.service.userInfo;

import com.example.Jinus.repository.v2.userInfo.DepartmentRepositoryV2;
import com.example.Jinus.service.v2.userInfo.DepartmentServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DepartmentServiceV2Test {
    @Autowired
    private DepartmentServiceV2 departmentServiceV2;
    @MockBean
    private DepartmentRepositoryV2 departmentRepositoryV2;

//    @Test
//    @DisplayName("학과 영문명 찾기")
//    public void checkDepartmentEng() {
//
//    }
}
