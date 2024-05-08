package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeteriaRepository extends JpaRepository<CafeteriaEntity, Integer> {
}
