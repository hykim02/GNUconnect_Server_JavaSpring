package com.example.Jinus.repository;

import com.example.Jinus.entity.CeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CeNoticeRepository extends JpaRepository<CeNoticeEntity, Integer> {
    List<CeNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
