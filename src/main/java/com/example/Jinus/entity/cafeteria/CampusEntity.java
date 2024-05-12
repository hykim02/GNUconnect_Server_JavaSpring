package com.example.Jinus.entity.cafeteria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "campus")
public class CampusEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "campus_name_ko")
    private String campusNameKo;
}
