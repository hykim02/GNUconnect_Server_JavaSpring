package com.example.Jinus.entity.notice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// it 공과대학
@Entity
@Getter
@Setter
@Table(name = "it-notice")
public class ItNoticeEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "ntt_sn")
    private Long nttSn;
}
