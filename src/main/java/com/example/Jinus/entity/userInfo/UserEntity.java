package com.example.Jinus.entity.userInfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 추가
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
