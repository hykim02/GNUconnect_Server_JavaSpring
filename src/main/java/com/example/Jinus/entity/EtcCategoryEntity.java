package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 건설환경공과대학
@Entity
@Getter
@Setter
@Table(name = "etc-category")
public class EtcCategoryEntity {
    @Id
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "department_id")
    private int departmentId;
}

