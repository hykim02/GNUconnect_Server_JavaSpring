package com.example.Jinus.repository;

import com.example.Jinus.entity.EtcCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtcCategoryRepository extends JpaRepository<EtcCategoryEntity, Integer> {
    List<EtcCategoryEntity> findByDepartmentId(int departmentId);
}
