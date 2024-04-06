package com.example.Jinus.repository;

import com.example.Jinus.entity.EtcCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtcCategoryRepository extends JpaRepository<EtcCategoryEntity, Integer> {
    List<EtcCategoryEntity> findByDepartmentId(int departmentId);
}
