package com.example.Jinus.repository.v2.notice;

import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeCategoryRepositoryV2 extends JpaRepository<NoticeCategoryEntity, Integer> {
    @Query("SELECT n FROM NoticeCategoryEntity n WHERE n.departmentId = :departmentId")
    List<NoticeCategoryEntity> findCategoryListByDepartmentId(int departmentId);
}
