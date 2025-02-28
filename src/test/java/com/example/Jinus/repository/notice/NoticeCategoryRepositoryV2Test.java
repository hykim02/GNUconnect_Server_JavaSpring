package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.repository.v2.notice.NoticeCategoryRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class NoticeCategoryRepositoryV2Test {

    @Autowired
    private NoticeCategoryRepositoryV2 noticeCategoryRepository;

    @BeforeEach
    void setUp() {
        noticeCategoryRepository.save(
                new NoticeCategoryEntity(1, 1, "취업", 12, 12, 12, "3/4"));
        noticeCategoryRepository.save(
                new NoticeCategoryEntity(2, 1, "공지", 12, 12, 12, "3/4"));
        noticeCategoryRepository.save(
                new NoticeCategoryEntity(3, 2, "공지", 12, 12, 12, "3/4"));
    }

    @Test
    @DisplayName("학과id에 해당하는 카테고리 리스트 찾기")
    public void checkCategoryListById() {
        // given
        int departmentId = 1;
        // 예상 데이터
        List<NoticeCategoryEntity> categoryList = new ArrayList<>();
        categoryList.add(new NoticeCategoryEntity(1, 1, "취업", 12, 12, 12, "3/4"));
        categoryList.add(new NoticeCategoryEntity(2, 1, "공지", 12, 12, 12, "3/4"));

        // when
        List<NoticeCategoryEntity> result = noticeCategoryRepository.findCategoryListByDepartmentId(departmentId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(categoryList);
    }

}
