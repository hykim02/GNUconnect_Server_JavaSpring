package com.example.Jinus.repository;

import com.example.Jinus.entity.InmunNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InmunNoticeRepository extends JpaRepository<InmunNoticeEntity, Integer> {
    List<InmunNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
