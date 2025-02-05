package com.example.Jinus.repository.v2.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepositoryV2 extends JpaRepository<CampusEntity, Integer> {

}
