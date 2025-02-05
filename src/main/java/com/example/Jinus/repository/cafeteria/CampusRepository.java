package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CampusRepository extends JpaRepository<CampusEntity, Integer> {
    @Query("SELECT c FROM CampusEntity c WHERE c.id <= 4")
    List<CampusEntity> findCampus();

    @Query("SELECT c.campusNameKo FROM CampusEntity c WHERE c.id = :campusId")
    String findCampusNameById(@Param("campusId") int campusId);

    @Query("SELECT c.id FROM CampusEntity c WHERE c.campusNameKo = :campusName")
    int findCampusIdByName(@Param("campusName") String campusName);
}
