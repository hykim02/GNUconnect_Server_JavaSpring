package com.example.Jinus.repository;

import com.example.Jinus.entity.CssNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CssNoticeRepository extends JpaRepository<CssNoticeEntity, Integer> {
    List<CssNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
