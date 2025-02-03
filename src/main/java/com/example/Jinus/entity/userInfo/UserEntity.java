package com.example.Jinus.entity.userInfo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "kakao-user")
public class UserEntity {
    @Id
    @Column(name = "id")
    private String kakaoId;

    @Column(name = "campus_id")
    private int campusId;

    @Column(name = "department_id")
    private int departmentId;
}
