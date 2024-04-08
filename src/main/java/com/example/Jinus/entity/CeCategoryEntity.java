package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 공과대학
@Entity
@Getter
@Setter
@Table(name = "ce-category")
public class CeCategoryEntity {
    @Id
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "department_id")
    private int departmentId;
}
