package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CeCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CeCategoryRepository extends JpaRepository<CeCategoryEntity, Integer> {
    List<CeCategoryEntity> findByDepartmentId(int departmentId);
}
