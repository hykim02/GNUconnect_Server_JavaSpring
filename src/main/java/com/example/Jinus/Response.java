package com.example.Jinus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data //자주 사용되는 메서드들 자동 생성
@NoArgsConstructor //매개변수가 없는 기본 생성자 자동 생성
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자 자동 생성
// table >> it-notice
public class Response {
    private Long id;
    private Long department_id; // 학과 id
    private Long category_id; // 공지 카테고리 id
    private String title; // 공지 제목
}

