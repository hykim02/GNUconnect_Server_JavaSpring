package com.example.Jinus.repository;

import com.example.Jinus.entity.BizNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizNoticeRepository extends JpaRepository<BizNoticeEntity, Integer>  {
    List<BizNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
