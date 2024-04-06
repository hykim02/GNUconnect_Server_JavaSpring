package com.example.Jinus.repository;

import com.example.Jinus.entity.EtcNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtcNoticeRepository extends JpaRepository<EtcNoticeEntity, Integer> {
    List<EtcNoticeEntity> findByDepartmentIdAndCategoryId(int departmentId, int categoryId);
}
