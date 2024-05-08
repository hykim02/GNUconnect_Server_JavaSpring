package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CssCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CssCategoryRepository extends JpaRepository<CssCategoryEntity, Integer> {
    List<CssCategoryEntity> findByDepartmentId(int departmentId);
}
