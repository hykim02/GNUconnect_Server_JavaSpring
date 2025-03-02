package com.example.Jinus.service.v2.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.repository.v2.notice.NoticeCategoryRepositoryV2;
import com.example.Jinus.service.v2.userInfo.UserServiceV2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeCategoryServiceV2 {

    private final NoticeCategoryRepositoryV2 noticeCategoryRepository;

    public NoticeCategoryServiceV2(NoticeCategoryRepositoryV2 noticeCategoryRepository) {
        this.noticeCategoryRepository = noticeCategoryRepository;
    }

    // 사용자 학과id와 일치하는 카테고리 찾기
    public List<Map<String, Object>> getCategoryEntity(int departmentId) {
        List<NoticeCategoryEntity> categoryEntities =
                noticeCategoryRepository.findCategoryListByDepartmentId(departmentId);
        return categoryListToMapList(categoryEntities);
    }

    // 카테고리 엔티티들 하나씩 hashmap으로 만들어 리스트로 저장하기
    public List<Map<String, Object>> categoryListToMapList(
            List<NoticeCategoryEntity> categoryEntities) {

        List<Map<String, Object>> categoryHashMapList = new ArrayList<>();

        for (NoticeCategoryEntity categoryEntity : categoryEntities) {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", categoryEntity.getId());
            hashMap.put("category", categoryEntity.getCategory());
            hashMap.put("mi", categoryEntity.getMi());
            hashMap.put("bbsId", categoryEntity.getBbsId());
            categoryHashMapList.add(hashMap);
        }
        return categoryHashMapList;
    }
 }
