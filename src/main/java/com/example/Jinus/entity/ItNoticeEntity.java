package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Table(name = "it-notice")
public class ItNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private String createdAt;
}
