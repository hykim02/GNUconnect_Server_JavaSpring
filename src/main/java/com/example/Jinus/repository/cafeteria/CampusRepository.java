package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends JpaRepository<CampusEntity, Integer> {
}
