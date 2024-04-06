package com.example.Jinus.repository;

import com.example.Jinus.entity.CnsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnsCategoryRepository extends JpaRepository<CnsCategoryEntity, Integer> {
    List<CnsCategoryEntity> findByDepartmentId(int departmentId);
}
