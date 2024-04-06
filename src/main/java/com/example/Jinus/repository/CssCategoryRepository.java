package com.example.Jinus.repository;

import com.example.Jinus.entity.CssCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CssCategoryRepository extends JpaRepository<CssCategoryEntity, Integer> {
    List<CssCategoryEntity> findByDepartmentId(int departmentId);
}
