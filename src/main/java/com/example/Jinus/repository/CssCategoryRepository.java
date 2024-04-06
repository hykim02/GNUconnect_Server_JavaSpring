package com.example.Jinus.repository;

import com.example.Jinus.entity.CssCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CssCategoryRepository extends JpaRepository<CssCategoryEntity, Integer> {
    List<CssCategoryEntity> findByDepartmentId(int departmentId);
}
