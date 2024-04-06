package com.example.Jinus.repository;

import com.example.Jinus.entity.BizNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BizNoticeRepository extends JpaRepository<BizNoticeEntity, Integer>  {
    List<BizNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
