package com.example.Jinus.repository;

import com.example.Jinus.entity.ItNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItNoticeRepository extends JpaRepository<ItNoticeEntity, Integer> {
    List<ItNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
