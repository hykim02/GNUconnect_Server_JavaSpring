package com.example.Jinus.repository;

import com.example.Jinus.entity.InmunCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InmunCategoryRepository extends JpaRepository<InmunCategoryEntity, Integer> {
    List<InmunCategoryEntity> findByDepartmentId(int departmentId);
}
