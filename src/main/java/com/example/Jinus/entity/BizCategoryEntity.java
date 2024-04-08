package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 경영대학
@Entity
@Getter
@Setter
@Table(name = "biz-category")
public class BizCategoryEntity {
    @Id
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "department_id")
    private int departmentId;
}
