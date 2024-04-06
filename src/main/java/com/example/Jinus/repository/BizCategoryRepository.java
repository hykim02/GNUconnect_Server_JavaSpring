package com.example.Jinus.repository;

import com.example.Jinus.entity.BizCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizCategoryRepository extends JpaRepository<BizCategoryEntity, Integer> {
    List<BizCategoryEntity> findByDepartmentId(int departmentId);
}
