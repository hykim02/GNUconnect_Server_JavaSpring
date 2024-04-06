package com.example.Jinus.repository;

import com.example.Jinus.entity.CalsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalsCategoryRepository extends JpaRepository<CalsCategoryEntity, Integer> {
    List<CalsCategoryEntity> findByDepartmentId(int departmentId);
}
