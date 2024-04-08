package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 농업생명과학대학
@Entity
@Getter
@Setter
@Table(name = "cals-category")
public class CalsCategoryEntity {
    @Id
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "department_id")
    private int departmentId;
}
