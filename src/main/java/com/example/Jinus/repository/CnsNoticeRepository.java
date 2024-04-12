package com.example.Jinus.repository;

import com.example.Jinus.entity.CnsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnsNoticeRepository extends JpaRepository<CnsNoticeEntity, Integer> {
    List<CnsNoticeEntity> findByCategoryId(int categoryId);
}
