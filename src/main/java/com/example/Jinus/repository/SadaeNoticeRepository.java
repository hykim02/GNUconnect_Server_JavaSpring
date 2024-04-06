package com.example.Jinus.repository;

import com.example.Jinus.entity.SadaeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SadaeNoticeRepository extends JpaRepository<SadaeNoticeEntity, Integer> {
    List<SadaeNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
