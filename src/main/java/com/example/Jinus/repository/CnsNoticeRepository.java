package com.example.Jinus.repository;

import com.example.Jinus.entity.CnsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CnsNoticeRepository extends JpaRepository<CnsNoticeEntity, Integer> {
    List<CnsNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
