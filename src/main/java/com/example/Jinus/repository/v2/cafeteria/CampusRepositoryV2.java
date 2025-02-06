package com.example.Jinus.repository.v2.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepositoryV2 extends JpaRepository<CampusEntity, Integer> {
    // 사용자 campusId로 캠퍼스 찾기
    @Query("SELECT c.campusNameKo FROM CampusEntity c WHERE c.id = :campusId")
    String findCampusNameById(@Param("campusId") int campusId);
}
