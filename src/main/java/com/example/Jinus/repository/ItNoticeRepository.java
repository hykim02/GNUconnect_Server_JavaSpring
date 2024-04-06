package com.example.Jinus.repository;

import com.example.Jinus.entity.ItNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItNoticeRepository extends JpaRepository<ItNoticeEntity, Integer> {
    List<ItNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
