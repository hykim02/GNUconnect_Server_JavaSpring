package com.example.Jinus.repository;

import com.example.Jinus.entity.MarsciCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarsciCategoryRepository extends JpaRepository<MarsciCategoryEntity, Integer> {
    List<MarsciCategoryEntity> findByDepartmentId(int departmentId);
}
