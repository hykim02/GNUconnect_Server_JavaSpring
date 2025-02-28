package com.example.Jinus.entity.notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notice_category")
public class NoticeCategoryEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "category")
    private String category;

    @Column(name = "mi")
    private int mi;

    @Column(name = "bbs_id")
    private int bbsId;

    @Column(name = "last_ntt_sn")
    private int lastNttSn;

    @Column(name = "updated_at")
    private String updatedAt;
}
