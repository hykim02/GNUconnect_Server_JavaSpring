package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


// 사회과학대학
@Entity
@Getter
@Setter
@Table(name = "css-notice")
public class CssNoticeEntity {
    @Id
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private String createdAt;
}
