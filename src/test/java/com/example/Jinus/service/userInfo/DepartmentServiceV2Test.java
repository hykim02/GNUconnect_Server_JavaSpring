package com.example.Jinus.service.userInfo;

import com.example.Jinus.repository.v2.userInfo.DepartmentRepositoryV2;
import com.example.Jinus.service.v2.userInfo.DepartmentServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceV2Test {
    @InjectMocks
    private DepartmentServiceV2 departmentServiceV2;
    @Mock
    private DepartmentRepositoryV2 departmentRepositoryV2;

    @Test
    @DisplayName("학과 영문명 찾기")
    public void checkDepartmentEng() {
        // given
        int id = 1;
        String eng = "it";

        // when
        Mockito.when(departmentRepositoryV2.findDepartmentEngById(id)).thenReturn(eng);
        String result = departmentServiceV2.getDepartmentEng(id);

        // then
        assertThat(result).isEqualTo(eng);
    }
}
