package com.example.Jinus.repository;

import com.example.Jinus.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
}
