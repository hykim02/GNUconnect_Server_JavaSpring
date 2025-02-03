package com.example.Jinus.repository.userInfo;

import com.example.Jinus.entity.userInfo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
