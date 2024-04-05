package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

// 건설환경공과대학
@Entity
@Getter
@Setter
@Table(name = "etc-category")
public class EtcCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department_id")
    private int departmentId;
}

