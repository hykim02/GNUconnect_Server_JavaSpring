package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CalsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalsCategoryRepository extends JpaRepository<CalsCategoryEntity, Integer> {
    List<CalsCategoryEntity> findByDepartmentId(int departmentId);
}
