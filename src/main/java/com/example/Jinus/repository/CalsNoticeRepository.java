package com.example.Jinus.repository;

import com.example.Jinus.entity.CalsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalsNoticeRepository extends JpaRepository<CalsNoticeEntity, Integer> {
    List<CalsNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
