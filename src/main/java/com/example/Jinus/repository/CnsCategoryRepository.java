package com.example.Jinus.repository;

import com.example.Jinus.entity.CnsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CnsCategoryRepository extends JpaRepository<CnsCategoryEntity, Integer> {
    List<CnsCategoryEntity> findByDepartmentId(int departmentId);
}
