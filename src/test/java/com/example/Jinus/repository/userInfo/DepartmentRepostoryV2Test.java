package com.example.Jinus.repository.userInfo;

import com.example.Jinus.entity.userInfo.DepartmentEntity;
import com.example.Jinus.repository.v2.userInfo.DepartmentRepositoryV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class DepartmentRepostoryV2Test {

    @Autowired
    private DepartmentRepositoryV2 departmentRepository;

    @Test
    @DisplayName("학과 영문명 찾기")
    public void checkDepartmentEng() {
        // given
        DepartmentEntity departmentEntity = new DepartmentEntity(1, 1, "it", "컴공", null, true);
        departmentRepository.save(departmentEntity);

        // when
        String result = departmentRepository.findDepartmentEngById(1);

        // then
        assertThat(result).isEqualTo("it");
    }
}
