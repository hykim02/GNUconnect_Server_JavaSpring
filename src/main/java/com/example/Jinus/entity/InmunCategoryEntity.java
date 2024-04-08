package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 인문대학
@Entity
@Getter
@Setter
@Table(name = "inmun-category")
public class InmunCategoryEntity {
    @Id
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "department_id")
    private int departmentId;
}
