package com.example.Jinus.service.notice;

import com.example.Jinus.repository.v2.notice.CategoryRepositoryV2;
import com.example.Jinus.service.v2.notice.CategoryServiceV2;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class NoticeCategoryServiceV2Test {

    @Autowired
    CategoryServiceV2 categoryService;
    @MockBean
    CategoryRepositoryV2 noticeCategoryRepository;

//    @Test
//    @DisplayName("사용자 학과id와 일치하는 카테고리 찾기")
//    public void checkCategoryByDepartmentId() {
//        // given
//        int departmentId = 1;
//        List<NoticeCategoryEntity> categoryList = new ArrayList<>();
//        categoryList.add(new NoticeCategoryEntity(1, 1, "취업", 12, 12, 12, "3/4"));
//        categoryList.add(new NoticeCategoryEntity(2, 1, "공지", 12, 12, 12, "3/4"));
//
//        // when
//        Mockito.when(noticeCategoryRepository.findCategoryListByDepartmentId(departmentId)).thenReturn(categoryList);
//        List<HashMap<String, Object>> result = categoryService.getCategoryEntity(departmentId);
//
//        // then
//        assertThat(result).usingRecursiveComparison().isEqualTo(categoryList);
//    }
}
