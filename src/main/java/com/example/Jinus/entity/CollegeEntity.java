package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "college")
public class CollegeEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "college_en")
    private String collegeEng;

    @Column(name = "campus_id")
    private int campusId;
}
