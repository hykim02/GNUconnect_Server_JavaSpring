package com.example.Jinus.service.v2.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.repository.v2.notice.NoticeCategoryRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeCategoryServiceV2 {

    private final NoticeCategoryRepositoryV2 noticeCategoryRepository;

    public NoticeCategoryServiceV2(NoticeCategoryRepositoryV2 noticeCategoryRepository) {
        this.noticeCategoryRepository = noticeCategoryRepository;
    }

    // 사용자 학과id와 일치하는 카테고리 찾기
    public List<NoticeCategoryEntity> getCategoryList(int departmentId) {
        return noticeCategoryRepository.findCategoryListByDepartmentId(departmentId);
    }
}
