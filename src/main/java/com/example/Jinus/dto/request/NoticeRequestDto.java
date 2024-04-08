package com.example.Jinus.dto.request;

// 학과 공지를 요청하는 request json

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequestDto {
    private UserRequest userRequest;

    // UserRequest와 User 클래스를 public으로 선언
    @Getter
    @Setter
    public static class UserRequest {
        private User user;

        // User 클래스를 public으로 선언
        @Getter
        @Setter
        public static class User {
            private String id; // 사용자 bot id
        }
    }
}

