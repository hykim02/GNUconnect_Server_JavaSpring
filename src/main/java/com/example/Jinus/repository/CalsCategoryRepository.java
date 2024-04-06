package com.example.Jinus.repository;

import com.example.Jinus.entity.CalsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalsCategoryRepository extends JpaRepository<CalsCategoryEntity, Integer> {
    List<CalsCategoryEntity> findByDepartmentId(int departmentId);
}
