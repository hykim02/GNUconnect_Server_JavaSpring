package com.example.Jinus.repository;

import com.example.Jinus.entity.CssNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CssNoticeRepository extends JpaRepository<CssNoticeEntity, Integer> {
    List<CssNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
