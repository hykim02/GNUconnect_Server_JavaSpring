package com.example.Jinus.repository;

import com.example.Jinus.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {
    @Query("SELECT c.collegeEng FROM CollegeEntity c")
    List<String> findCollegeEn();
}
