package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.NoticeEntity;
import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class NoticeRepositoryV2Test {

    @Autowired
    NoticeRepositoryV2 noticeRepositoryV2;

    @BeforeEach
    void setUp() {
        noticeRepositoryV2.save(new NoticeEntity(1, 10, "게시물1", 12, "3/4"));
        noticeRepositoryV2.save(new NoticeEntity(2, 10, "게시물1", 12, "3/4"));

    }
}
