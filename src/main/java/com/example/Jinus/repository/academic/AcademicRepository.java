package com.example.Jinus.repository.academic;

import com.example.Jinus.entity.academic.AcademicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicRepository extends JpaRepository<AcademicEntity, Integer> {
    @Query( value = "SELECT * " +
                    "FROM academic_calendar a " +
                    "WHERE (EXTRACT(MONTH FROM a.end_date ) = :currentMonth " +
                    "OR EXTRACT(MONTH FROM a.start_date) = :currentMonth) " +
                    "AND EXTRACT(YEAR FROM a.start_date) = :currentYear " +
                    "AND a.calendar_type = 1 ",
            nativeQuery = true)
    List<AcademicEntity> findCalendarEntities(@Param("currentMonth") int currentMonth,
                                              @Param("currentYear") int currentYear);
}
