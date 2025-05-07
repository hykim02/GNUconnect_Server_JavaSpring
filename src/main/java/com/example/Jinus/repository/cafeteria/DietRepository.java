package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<CafeteriaDietEntity, Integer> {
    @Query("SELECT new com.example.Jinus.dto.data.DietDto(c.dishCategory, c.dishName, c.dishType) " +
            "FROM CafeteriaDietEntity c " +
            "WHERE c.dietDate = :dietDate " +
            "AND c.time = :period AND c.cafeteriaId = :cafeteriaId")
    List<DietDto> findDietList(@Param("dietDate") Date dietDate,
                               @Param("period") String period,
                               @Param("cafeteriaId") int cafeteriaId);
}
