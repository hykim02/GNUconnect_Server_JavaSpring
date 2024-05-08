package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.BizNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BizNoticeRepository extends JpaRepository<BizNoticeEntity, Integer>  {
    List<BizNoticeEntity> findByCategoryId(int categoryId);
}
