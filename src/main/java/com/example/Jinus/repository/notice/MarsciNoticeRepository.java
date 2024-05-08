package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.MarsciNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarsciNoticeRepository extends JpaRepository<MarsciNoticeEntity, Integer> {
    List<MarsciNoticeEntity> findByCategoryId(int categoryId);
}
