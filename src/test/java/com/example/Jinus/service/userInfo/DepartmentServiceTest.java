package com.example.Jinus.service.userInfo;

import com.example.Jinus.repository.userInfo.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @InjectMocks
    private DepartmentService departmentServiceV2;
    @Mock
    private DepartmentRepository departmentRepositoryV2;

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
