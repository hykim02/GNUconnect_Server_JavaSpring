package com.example.Jinus.repository.v2.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepositoryV2 extends JpaRepository<CampusEntity, Integer> {
    // 사용자 campusId로 캠퍼스 이름 찾기
    @Query("SELECT c.campusNameKo FROM CampusEntity c WHERE c.id = :campusId")
    String findCampusNameByCampusId(@Param("campusId") int campusId);

    // campusId가 5보다 작은 캠퍼스들 찾기
    @Query("SELECT c FROM CampusEntity c WHERE c.id < 5")
    List<CampusEntity> findCampusList();

    // campusName으로 id 찾기
    @Query("SELECT c.id FROM CampusEntity c WHERE c.campusNameKo = :campusName")
    int findCampusIdByName(@Param("campusName")String campusName);
}
