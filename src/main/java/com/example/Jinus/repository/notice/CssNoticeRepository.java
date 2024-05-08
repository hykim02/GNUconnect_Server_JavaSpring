package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.CssNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CssNoticeRepository extends JpaRepository<CssNoticeEntity, Integer> {
    List<CssNoticeEntity> findByCategoryId(int categoryId);
}
