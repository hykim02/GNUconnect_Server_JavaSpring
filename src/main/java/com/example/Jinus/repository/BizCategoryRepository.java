package com.example.Jinus.repository;

import com.example.Jinus.entity.BizCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BizCategoryRepository extends JpaRepository<BizCategoryEntity, Integer> {
    List<BizCategoryEntity> findByDepartmentId(int departmentId);
}
