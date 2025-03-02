package com.example.Jinus.entity.userInfo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class DepartmentEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "college_id")
    private int collegeId;

    @Column(name = "department_en")
    private String departmentEn;

    @Column(name = "department_ko")
    private String departmentKo;

    @Column(name = "parent_department_id")
    private Integer parentDepartmentId;

    @Column(name = "is_active")
    private boolean isActive;
}
