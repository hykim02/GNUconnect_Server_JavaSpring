package com.example.Jinus.repository.v2.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface CafeteriaRepositoryV2 extends JpaRepository<CafeteriaEntity, Integer> {
    // 사용자 campusId와 동일한 식당이름과 url 찾기
    @Query("SELECT c.cafeteriaNameKo, c.thumbnailUrl FROM CafeteriaEntity c WHERE c.campusId = :campusId")
    List<Object[]> findCafeteriaListByCampusId(@Param("campusId")int campusId);

    // campusId와 식당이름으로 cafeteriaId 찾기
    @Query("SELECT c.id FROM CafeteriaEntity c " +
            "WHERE c.campusId = :campusId AND c.cafeteriaNameKo = :cafeteriaName")
    Optional<Integer> findCafeteriaId(@Param("cafeteriaName") String cafeteriaName,
                                      @Param("campusId") int campusId);
}
