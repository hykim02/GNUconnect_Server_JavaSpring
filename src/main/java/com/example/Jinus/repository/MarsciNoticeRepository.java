package com.example.Jinus.repository;

import com.example.Jinus.entity.MarsciNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarsciNoticeRepository extends JpaRepository<MarsciNoticeEntity, Integer> {
    List<MarsciNoticeEntity> findByCategoryId(int categoryId);
}
