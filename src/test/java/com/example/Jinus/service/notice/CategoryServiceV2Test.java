package com.example.Jinus.service.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.repository.v2.notice.CategoryRepositoryV2;
import com.example.Jinus.service.v2.notice.CategoryServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceV2Test {

    @Mock
    private CategoryRepositoryV2 noticeCategoryRepository;
    @InjectMocks
    private CategoryServiceV2 categoryService;

    @Test
    @DisplayName("사용자 학과id와 일치하는 카테고리 찾기")
    void checkGetCategoryEntityTest() {
        // Given - 테스트 데이터 생성
        int departmentId = 1;
        List<NoticeCategoryEntity> expectedCategories = List.of(
                new NoticeCategoryEntity(1, 1, "공지사항", 0, 0, 0, null),
                new NoticeCategoryEntity(2, 1, "학사일정", 0, 0, 0, null)
        );

        // Mocking - 목 객체의 동작 정의
        when(noticeCategoryRepository.findCategoryListByDepartmentId(departmentId))
                .thenReturn(expectedCategories);

        // When - 서비스 호출
        List<NoticeCategoryEntity> result = categoryService.getCategoryEntity(departmentId);

        // Then - 결과 검증
        assertEquals(expectedCategories.size(), result.size()); // 2개의 카테고리 반환해야 함
        assertEquals(expectedCategories, result);
        // 서비스 메소드 중복 호출 방지를 위한 호출 횟수 확인
        verify(noticeCategoryRepository, times(1))
                .findCategoryListByDepartmentId(departmentId);
    }
}
