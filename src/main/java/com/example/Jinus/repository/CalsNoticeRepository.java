package com.example.Jinus.repository;

import com.example.Jinus.entity.CalsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalsNoticeRepository extends JpaRepository<CalsNoticeEntity, Integer> {
    List<CalsNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
