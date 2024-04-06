package com.example.Jinus.repository;

import com.example.Jinus.entity.BizCategoryEntity;
import com.example.Jinus.entity.ItCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItCategoryRepository extends JpaRepository<ItCategoryEntity, Integer> {
    List<ItCategoryEntity> findByDepartmentId(int departmentId);
}
