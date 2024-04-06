package com.example.Jinus.repository;

import com.example.Jinus.entity.SadaeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SadaeCategoryRepository extends JpaRepository<SadaeCategoryEntity, Integer> {
    List<SadaeCategoryEntity> findByDepartmentId(int departmentId);
}
