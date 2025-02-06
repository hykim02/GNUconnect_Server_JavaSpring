package com.example.Jinus.repository.v2.userInfo;

import com.example.Jinus.entity.userInfo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryV2 extends JpaRepository<UserEntity, String> {
    // 사용자 등록여부 확인
    @Query("SELECT u.campusId FROM UserEntity u WHERE u.kakaoId = :kakaoId")
    Optional<Integer> findCampusIdById(@Param("kakaoId") String id);
}
