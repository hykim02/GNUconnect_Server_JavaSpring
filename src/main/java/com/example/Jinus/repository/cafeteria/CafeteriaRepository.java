package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeteriaRepository extends JpaRepository<CafeteriaEntity, Integer> {
    @Query(
            "SELECT c.id " +
            "FROM CafeteriaEntity c " +
            "WHERE c.cafeteriaNameKo = :name"
    )
    int findIdByName(@Param("name") String name);

    @Query(
            "SELECT c.id " +
            "FROM CafeteriaEntity c " +
            "WHERE c.cafeteriaNameKo = :name and c.campusId = :campusId"
    )
    Integer findIdByNameAndCampusId(@Param("name") String name, @Param("campusId") int campusId);

    @Query("SELECT c.campusId FROM CafeteriaEntity c WHERE c.cafeteriaNameKo = :cafeteriaName")
    int findCampusId(@Param("cafeteriaName") String cafeteriaName);

    @Query("SELECT c.thumbnailUrl FROM CafeteriaEntity c WHERE c.id = :cafeteriaId")
    String findThumnailUrl(@Param("cafeteriaId")int cafeteriaId);
}
