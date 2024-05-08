package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.EtcNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtcNoticeRepository extends JpaRepository<EtcNoticeEntity, Integer> {
    List<EtcNoticeEntity> findByCategoryId(int categoryId);
}
