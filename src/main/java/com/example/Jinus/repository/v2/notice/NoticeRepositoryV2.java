package com.example.Jinus.repository.v2.notice;

import com.example.Jinus.entity.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepositoryV2 extends JpaRepository<NoticeEntity, Integer> {
    @Query("SELECT n FROM NoticeEntity n WHERE n.categoryId = :categoryId " +
            "ORDER BY n.createdAt DESC LIMIT 4")
    List<NoticeEntity> findNoticeListByCategoryId(@Param("categoryId")int categoryId);
}
