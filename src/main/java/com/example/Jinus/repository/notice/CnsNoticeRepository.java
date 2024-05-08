package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CnsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CnsNoticeRepository extends JpaRepository<CnsNoticeEntity, Integer> {
    List<CnsNoticeEntity> findByCategoryId(int categoryId);
}
