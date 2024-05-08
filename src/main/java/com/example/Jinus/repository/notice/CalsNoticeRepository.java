package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CalsNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalsNoticeRepository extends JpaRepository<CalsNoticeEntity, Integer> {
    List<CalsNoticeEntity> findByCategoryId(int categoryId);
}
