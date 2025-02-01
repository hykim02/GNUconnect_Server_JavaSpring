package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface CafeteriaDietRepository extends JpaRepository<CafeteriaDietEntity, Integer> {
    @Query(
            value = "SELECT * " +
                    "FROM cafeteria_diet c " +
                    "WHERE c.cafeteria_id = :cafeteriaId " +
                    "AND c.date = :date " +
                    "AND c.time = :time ",
            nativeQuery = true)

    List<CafeteriaDietEntity> findCategoryOrType(@Param("date") LocalDate date,
                                                 @Param("time") String time,
                                                 @Param("cafeteriaId") int cafeteriaId);
}

