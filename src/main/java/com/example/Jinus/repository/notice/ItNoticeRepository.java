package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.ItNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItNoticeRepository extends JpaRepository<ItNoticeEntity, Integer> {
    List<ItNoticeEntity> findByCategoryId(int categoryId);
}
