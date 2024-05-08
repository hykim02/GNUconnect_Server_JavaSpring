package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.InmunCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InmunCategoryRepository extends JpaRepository<InmunCategoryEntity, Integer> {
    List<InmunCategoryEntity> findByDepartmentId(int departmentId);
}
