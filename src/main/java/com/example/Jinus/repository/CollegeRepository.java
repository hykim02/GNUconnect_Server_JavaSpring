package com.example.Jinus.repository;

import com.example.Jinus.entity.CollegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {

}
