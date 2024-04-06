package com.example.Jinus.repository;

import com.example.Jinus.entity.CeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CeCategoryRepository extends JpaRepository<CeCategoryEntity, Integer> {
    List<CeCategoryEntity> findByDepartmentId(int departmentId);
}
