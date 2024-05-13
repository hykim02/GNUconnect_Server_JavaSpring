package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public interface CafeteriaDietRepository extends JpaRepository<CafeteriaDietEntity, Integer> {
//    @Query(
//            value = "SELECT c.dish_category AS dishCategory, c.dish_name AS dishName " +
//                    "FROM \"cafeteria-diet\" c " +
//                    "WHERE DATE_TRUNC('day', c.date) = :date " +
//                    "AND c.time = :time AND c.cafeteria_id = :cafeteriaId",
//            nativeQuery = true
//    )
//    List<HashMap<String, String>> findCategoryAndDiet(@Param("date") LocalDate date,
//                                                      @Param("time") String time,
//                                                      @Param("cafeteriaId") int cafeteriaId);

    @Query(
            value = "SELECT c.dish_category AS dishCategory, c.dish_name AS dishName " +
                    "FROM \"cafeteria-diet\" c " +
                    "WHERE DATE_TRUNC('day', c.date) = :date " +
                    "AND c.time = :time AND c.cafeteria_id = :cafeteriaId",
            nativeQuery = true
    )
    List<String> findCategoryAndDiet(@Param("date") LocalDate date,
                                                      @Param("time") String time,
                                                      @Param("cafeteriaId") int cafeteriaId);
}

