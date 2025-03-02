package com.example.Jinus.repository.v2.userInfo;

import com.example.Jinus.entity.userInfo.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositoryV2 extends JpaRepository<DepartmentEntity, Integer> {
    // 학과 영문명 찾기
    @Query("SELECT d.departmentEn FROM DepartmentEntity d WHERE d.id = :id")
    String findDepartmentEngById(@Param("id")Integer id);
}
