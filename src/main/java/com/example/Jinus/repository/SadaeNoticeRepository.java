package com.example.Jinus.repository;

import com.example.Jinus.entity.SadaeNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SadaeNoticeRepository extends JpaRepository<SadaeNoticeEntity, Integer> {
    List<SadaeNoticeEntity> findByCategoryId(int categoryId);
}
