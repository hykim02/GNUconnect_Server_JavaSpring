package com.example.Jinus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "kakao_id")
    private String kakaoId;

    @Column(name = "department_id")
    private int departmentId;
}
