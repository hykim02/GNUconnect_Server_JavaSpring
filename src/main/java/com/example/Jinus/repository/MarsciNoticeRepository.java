package com.example.Jinus.repository;

import com.example.Jinus.entity.MarsciNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarsciNoticeRepository extends JpaRepository<MarsciNoticeEntity, Integer> {
    List<MarsciNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
