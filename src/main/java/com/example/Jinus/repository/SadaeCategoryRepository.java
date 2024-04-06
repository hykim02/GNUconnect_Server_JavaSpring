package com.example.Jinus.repository;

import com.example.Jinus.entity.SadaeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadaeCategoryRepository extends JpaRepository<SadaeCategoryEntity, Integer> {
    List<SadaeCategoryEntity> findByDepartmentId(int departmentId);
}
