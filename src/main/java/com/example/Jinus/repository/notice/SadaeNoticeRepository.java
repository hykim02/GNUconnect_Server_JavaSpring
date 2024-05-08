package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.SadaeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadaeNoticeRepository extends JpaRepository<SadaeNoticeEntity, Integer> {
    List<SadaeNoticeEntity> findByCategoryId(int categoryId);
}
