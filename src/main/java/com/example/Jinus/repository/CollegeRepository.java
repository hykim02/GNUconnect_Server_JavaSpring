package com.example.Jinus.repository;

import com.example.Jinus.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {
    @Query("SELECT c.campusId FROM CollegeEntity c WHERE c.id = :collegeId")
    int findCampusId(@Param("collegeId") int collegeId);
}
