package com.example.Jinus.repository;

import com.example.Jinus.entity.CeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CeNoticeRepository extends JpaRepository<CeNoticeEntity, Integer> {
    List<CeNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
