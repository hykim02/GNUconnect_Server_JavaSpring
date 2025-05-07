package com.example.Jinus.repository.userInfo;

import com.example.Jinus.entity.userInfo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // 사용자 등록여부 확인(존재한다면 campusId 찾기)
    @Query("SELECT u.campusId FROM UserEntity u WHERE u.kakaoId = :kakaoId")
    Optional<Integer> findCampusIdById(@Param("kakaoId") String id);

    // 사용자 학과id 찾기
    @Query("SELECT u.departmentId FROM UserEntity u WHERE u.kakaoId = :kakaoId")
    Optional<Integer> findDepartmentIdById(@Param("kakaoId") String id);
}
