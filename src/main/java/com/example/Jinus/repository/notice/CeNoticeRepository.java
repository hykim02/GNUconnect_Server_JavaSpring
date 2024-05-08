package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CeNoticeRepository extends JpaRepository<CeNoticeEntity, Integer> {
    List<CeNoticeEntity> findByCategoryId(int categoryId);
}
