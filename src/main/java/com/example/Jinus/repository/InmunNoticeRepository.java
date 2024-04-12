package com.example.Jinus.repository;

import com.example.Jinus.entity.InmunNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InmunNoticeRepository extends JpaRepository<InmunNoticeEntity, Integer> {
    List<InmunNoticeEntity> findByCategoryId(int categoryId);
}
