package com.example.Jinus.service.v2.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.repository.v2.notice.CategoryRepositoryV2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceV2 {

    private final CategoryRepositoryV2 noticeCategoryRepository;

    public CategoryServiceV2(CategoryRepositoryV2 noticeCategoryRepository) {
        this.noticeCategoryRepository = noticeCategoryRepository;
    }

    // 사용자 학과id와 일치하는 카테고리 찾기
    public List<NoticeCategoryEntity> getCategoryEntity(int departmentId) {
        return noticeCategoryRepository.findCategoryListByDepartmentId(departmentId);
    }
 }
